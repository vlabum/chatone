package ru.vlabum.chatone;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;

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
        final String string = "{\"login\":\"vlabum\"}";
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node =  mapper.readTree(string);
        JsonNode ret = node.findValue("login");
        if (ret != null)
            System.out.println(ret.toString());
        System.out.println("end");
    }
}
