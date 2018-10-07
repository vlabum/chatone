package ru.vlabum.chatone.server.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.User;
import ru.vlabum.chatone.server.api.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
@NoArgsConstructor
public class UserServiceBean implements UserService {

    @NotNull
    private Map<String, User> users = new LinkedHashMap<>();

    @PostConstruct
    private void init(){
        registry("user1", "user1");
        registry("user2", "user2");
        registry("user3", "user3");
        registry("user4", "user4");
    }

    @Override
    public boolean registry(@Nullable final String login, @Nullable final String password){
        if (login == null || login.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;
        if (exists(login)) return false;
        @NotNull final User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        users.put(login, user);
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
