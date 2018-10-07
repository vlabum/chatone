package ru.vlabum.chatone.config;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;

@Getter
@Setter
@ApplicationScoped
public class ChatConfig {
    private Integer port = 8080;
    private String host = "localhost";
    private String login;
}
