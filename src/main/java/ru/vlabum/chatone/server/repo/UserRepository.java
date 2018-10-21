package ru.vlabum.chatone.server.repo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import ru.vlabum.chatone.server.model.User;
import java.util.List;

public interface UserRepository {

    @Select("SELECT * FROM `User`")
    List<User> getAll();

    @Update("UPDATE `User` SET `login` = #{login} WHERE `id` = #{id}")
    void updateLogin(User user);

    @Insert("INSERT INTO `User` (id, login, password, email, nick)" +
            "VALUES(#{id}, #{login}, #{password}, #{email}, #{nick});")
    void insertUser(User user);

}
