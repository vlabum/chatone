package ru.vlabum.chatone.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class User {

    @NotNull
    private String id = UUID.randomUUID().toString();

    @Nullable
    private String login;

    @Nullable
    private String password;

    @Nullable
    private String email;

    @Nullable
    private String nick;

    public User(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    public String toString() {
        @NotNull final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=");
        stringBuilder.append(id);
        stringBuilder.append(";login=");
        stringBuilder.append(login);
        stringBuilder.append(";email=");
        stringBuilder.append(email);
        stringBuilder.append(";nick=");
        stringBuilder.append(nick);
        return stringBuilder.toString();
    }


}
