package code.hcs.rpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import code.hcs.rpc.handler.DefaultMessageHandler;
import code.hcs.rpc.handler.MessageHandler;
import code.hcs.rpc.protocol.ProtocolFactory;
import code.hcs.rpc.service.HelloWordImpl;

/**
 * ${DESCRIPTION}
 * package PACKAGE_NAME
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 11:11
 **/
public abstract class Application {


    public void run(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("must give two args: listenPort | maxThreads");
        }
        int listenPort = Integer.parseInt(args[0]);
        int maxThreads = Integer.parseInt(args[1]);

        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
        Server server = getServer();
        MessageHandler messageHandler = ProtocolFactory.getMessageHandler(DefaultMessageHandler.TYPE);
        messageHandler.registerProcessor("helloWorld", new HelloWordImpl());
        server.registerMessageHandler(messageHandler);
        //start netty server
        server.start(listenPort, executorService);
    }


    public abstract Server getServer();

}
