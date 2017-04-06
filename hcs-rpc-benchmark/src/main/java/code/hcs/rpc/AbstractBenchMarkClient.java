package code.hcs.rpc;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 16:59
 **/
public abstract class AbstractBenchMarkClient {


    public abstract <T> T getProxyInstance(String serviceName, int timeout, int protocolType, int codecType, List<InetSocketAddress> servers);
}
