package code.hcs.rpc;

import java.util.concurrent.ExecutorService;

import code.hcs.rpc.handler.MessageHandler;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 15:24
 **/
public interface Server {

    public void start(int listenPort, final ExecutorService executorService) throws Exception;


    public void close();

    public void registerMessageHandler(MessageHandler messageHandler);

}
