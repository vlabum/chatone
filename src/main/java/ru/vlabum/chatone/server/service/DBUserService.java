package ru.vlabum.chatone.server.service;

import ru.vlabum.chatone.server.model.User;
import ru.vlabum.chatone.server.repo.UserRepository;
import java.io.IOException;
import java.util.List;

public final class DBUserService extends DBAbstractService {

    private final UserRepository userRepository;

    public DBUserService () throws IOException {
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void updateLogin(final User user) {
        userRepository.updateLogin(user);
    }

    public void inserUser(final User user) {
        userRepository.insertUser(user);
    }

    public void commit() {
        sqlSession.commit();
    }

    public void rollback() {
        sqlSession.rollback();
    }

}