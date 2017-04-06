package code.hcs.rpc.client;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * connection pool factory
 * <pre>
 *     ${@Link common pool2}
 * </pre>
 * package code.hcs.rpc.client
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:21
 **/
public class NettyClientConnPoolFactory extends BasePooledObjectFactory<NettyClientPoolProxy> {
    private String host;
    private int port;
    private NettyClient nettyClient;

    public NettyClientConnPoolFactory(String host, int port, NettyClient nettyClient) {
        this.host = host;
        this.port = port;
        this.nettyClient = nettyClient;
    }

    @Override
    public NettyClientPoolProxy create() throws Exception {
        NettyClientPoolProxy proxy = new NettyClientPoolProxy();
        proxy.createProxy(host, port, nettyClient);
        return proxy;
    }

    @Override
    public PooledObject<NettyClientPoolProxy> wrap(NettyClientPoolProxy nettyClientPoolProxy) {
        return new DefaultPooledObject<NettyClientPoolProxy>(nettyClientPoolProxy);
    }

    @Override
    public void destroyObject(PooledObject<NettyClientPoolProxy> p) throws Exception {
        NettyClientPoolProxy proxy = p.getObject();
        proxy.close();

    }

    @Override
    public boolean validateObject(PooledObject<NettyClientPoolProxy> p) {
        NettyClientPoolProxy proxy = p.getObject();
        return proxy.isValidate();
    }
}
