package ru.vlabum.chatone;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import ru.vlabum.chatone.server.model.User;
import ru.vlabum.chatone.server.service.DBUserService;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    @SneakyThrows
    public void shouldAnswerWithTrue()
    {
        final DBUserService dbUserService = new DBUserService();
        final List<User> users = dbUserService.getAll();
        for (final User user : users) {
            System.out.println(user.toString());
        }
    }
}
