package code.hcs.rpc.protocol;

import code.hcs.rpc.handler.DefaultMessageHandler;
import code.hcs.rpc.handler.MessageHandler;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.protocol
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 17:54
 **/
public class ProtocolFactory {

    private static Protocol[] protocols = new Protocol[2];
    private static MessageHandler[] handlers = new MessageHandler[2];

    static {
        registerProtocol(DefaultProtocol.TYPE, new DefaultProtocol());
        registerHandler(DefaultMessageHandler.TYPE, new DefaultMessageHandler());
    }

    public static void registerProtocol(int protocolType, Protocol protocol) {
        if (protocolType > protocols.length) {
            Protocol[] newProtocols = new Protocol[protocolType + 1];
            System.arraycopy(protocols, 0, newProtocols, 0, newProtocols.length);
            protocols = newProtocols;
        }
        protocols[protocolType] = protocol;
    }

    public static void registerHandler(int handlerType, MessageHandler messageHandler) {
        if (handlerType > handlers.length) {
            MessageHandler[] newHandlers = new MessageHandler[handlerType + 1];
            System.arraycopy(handlers, 0, newHandlers, 0, handlers.length);
            handlers = newHandlers;
        }
        handlers[handlerType] = messageHandler;
    }


    public static Protocol getProtocol(int protocolType) {
        return protocols[protocolType];
    }


    public static MessageHandler getMessageHandler(int handlerType) {
        return handlers[handlerType];
    }
}
