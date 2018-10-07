package ru.vlabum.chatone;

import ru.vlabum.chatone.api.ChatApp;
import ru.vlabum.chatone.client.service.ClientService;
import ru.vlabum.chatone.server.service.ServerServiceBean;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( final String[] args ){
        ChatApp app = getApp(args);
        app.run();
    }

    public static ChatApp getApp(final String[] args){
        SeContainerInitializer.newInstance().addPackages(App.class).initialize();

        if (args == null || args.length == 0) return CDI.current().select(ClientService.class).get();
        if (args.length == 1 || "server".equals(args[0])) return CDI.current().select(ServerServiceBean.class).get();
        return CDI.current().select(ClientService.class).get();
    }
}
