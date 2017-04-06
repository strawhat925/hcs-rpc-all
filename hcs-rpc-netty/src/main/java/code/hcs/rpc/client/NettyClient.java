package code.hcs.rpc.client;


import org.apache.commons.pool2.impl.GenericObjectPool;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.RpcCallbackFuture;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.client
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:06
 **/
public class NettyClient {

    private String host;
    private int port;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public ResponseWapper sendReqeust(RequestWapper requestWapper) throws Exception {
        GenericObjectPool<NettyClientPoolProxy> pool = NettyClientPool.getPool(host, port, NettyClient.this);

        NettyClientPoolProxy nettyClientPoolProxy = null;

        try {
            RpcCallbackFuture future = new RpcCallbackFuture(requestWapper);
            RpcCallbackFuture.futurePool.put(requestWapper.getId(), future);

            nettyClientPoolProxy = pool.borrowObject();
            nettyClientPoolProxy.sendRequest(requestWapper);

            return future.get(requestWapper.getTimeout());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            RpcCallbackFuture.futurePool.remove(requestWapper.getId());
            if (pool != null) {
                pool.returnObject(nettyClientPoolProxy);
            }
        }
    }

}
