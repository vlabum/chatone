package ru.vlabum.chatone;

import ru.vlabum.chatone.api.ChatApp;
import ru.vlabum.chatone.client.service.ClientService;
import ru.vlabum.chatone.server.service.ServerServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( final String[] args ){
        if (args.length > 0)
            logger.info("Start main %s", args[0]);
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
