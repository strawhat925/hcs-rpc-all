package code.hcs.rpc.handler;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;

/**
 * processing request message inter
 * package code.hcs.rpc.handler
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 9:52
 **/
public interface MessageHandler {

    public ResponseWapper handleRequest(RequestWapper requestWapper);

    public void registerProcessor(String instanceName, Object instance);
}
