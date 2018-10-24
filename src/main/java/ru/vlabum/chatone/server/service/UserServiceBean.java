package ru.vlabum.chatone.server.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.server.model.Connection;
import ru.vlabum.chatone.server.model.User;
import ru.vlabum.chatone.server.api.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ApplicationScoped
@NoArgsConstructor
public class UserServiceBean implements UserService {

    @Inject
    private ConnectionServiceBean connectionService;

    @NotNull
    private Map<String, User> users = new LinkedHashMap<>();

    @PostConstruct
    private void init(){
        try {
            final DBUserService dbUserService = new DBUserService();
            final List<User> usersDB = dbUserService.getAll();
            for (User user : usersDB) {
                users.put(user.getLogin(), user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Изменение текущего логина.
     * @param oldLogin
     * @param newLogin
     * @return
     * @throws IOException
     */
    @Override
    public boolean updateLogin(
            @Nullable final Socket socket,
            @Nullable final String oldLogin,
            @Nullable final String newLogin
    ) throws IOException {
        if (socket == null) return false;
        if (oldLogin == null || oldLogin.isEmpty()) return false;
        if (newLogin == null || newLogin.isEmpty()) return false;
        if (!exists(oldLogin)) return false;
        if (exists(newLogin)) return false;
        final DBUserService dbUserService = new DBUserService();
        @Nullable final Connection connection = connectionService.get(socket);
        if (connection == null) return false;
        try {
            final User user = users.get(oldLogin);
            user.setLogin(newLogin);
            dbUserService.updateLogin(user);
            users.remove(oldLogin);
            users.put(user.getLogin(),user);
            connection.setLogin(newLogin);
            dbUserService.commit();
        } catch (Exception e) {
            dbUserService.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Регистрация нового пользователя. Одновременно пишется и в Map и в MySql.
     * @param login
     * @param password
     * @return
     * @throws IOException
     */
    @Override
    public boolean registry(@Nullable final String login, @Nullable final String password) throws IOException  {
        if (login == null || login.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;
        if (exists(login)) return false;
        @NotNull final User user = new User(login, password);

        final DBUserService dbUserService = new DBUserService();
        try {
            dbUserService.inserUser(user);
            users.put(user.getLogin(), user);
            dbUserService.commit();
        } catch (Exception e) {
            dbUserService.rollback();
            e.printStackTrace();
            return false;
        }

        System.out.println("Added new user");
        return true;
    }

    @Override
    public boolean exists(@Nullable final String login){
        if (login == null || login.isEmpty()) return false;
        return users.containsKey(login);
    }

    @Nullable
    @Override
    public User findByLogin(@Nullable final String login){
        if (login == null || login.isEmpty()) return null;
        return users.get(login);
    }

    @Override
    public boolean check(@Nullable final String login, @Nullable final String password){
        if (login == null || login.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;
        @NotNull final User user = findByLogin(login);
        if (user == null) return false;
        return password.equals(user.getPassword());
    }

}
