package code.hcs.rpc.client;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.ConcurrentHashMap;

import code.hcs.rpc.util.PropertiesUtil;

/**
 * used the common pool to manage the netty client connection number
 * package code.hcs.rpc.client
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:17
 **/
public class NettyClientPool {

    private GenericObjectPool<NettyClientPoolProxy> pool;

    public NettyClientPool(String host, int port, NettyClient nettyClient) {
        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        ////池对象耗尽之后是否阻塞,maxWait<0时一直等待
        poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(PropertiesUtil.getProperty("netty.blockWhenExhausted")));
        //最大等待时间
        poolConfig.setMaxWaitMillis(Long.parseLong(PropertiesUtil.getProperty(("netty.maxWait"))));
        ////最小空闲
        poolConfig.setMinIdle(Integer.parseInt(PropertiesUtil.getProperty(("netty.minIdle"))));
        //最大空闲
        poolConfig.setMaxIdle(Integer.parseInt(PropertiesUtil.getProperty(("netty.maxIdle"))));
        //最大数
        poolConfig.setMaxTotal(Integer.parseInt(PropertiesUtil.getProperty(("netty.maxTotal"))));
        //取对象是验证
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(PropertiesUtil.getProperty(("netty.testOnBorrow"))));
        //回收验证
        poolConfig.setTestOnReturn(Boolean.parseBoolean(PropertiesUtil.getProperty(("netty.testOnReturn"))));
        //创建时验证
        poolConfig.setTestOnCreate(Boolean.parseBoolean(PropertiesUtil.getProperty(("netty.testOnCreate"))));
        //空闲验证
        poolConfig.setTestWhileIdle(Boolean.parseBoolean(PropertiesUtil.getProperty(("netty.testWhileIdle"))));
        //后进先出
        poolConfig.setLifo(Boolean.parseBoolean(PropertiesUtil.getProperty(("netty.lifo"))));
        pool = new GenericObjectPool<NettyClientPoolProxy>(new NettyClientConnPoolFactory(host, port, nettyClient), poolConfig);

    }


    private GenericObjectPool<NettyClientPoolProxy> getPool() {
        return pool;
    }


    private static ConcurrentHashMap<String, NettyClientPool> clientPoolMap = new ConcurrentHashMap<String, NettyClientPool>();

    public static GenericObjectPool<NettyClientPoolProxy> getPool(String host, int port, NettyClient nettyClient) {
        String key = host + ":" + host;
        NettyClientPool nettyClientPool = clientPoolMap.get(key);
        if (nettyClientPool != null) {
            return nettyClientPool.getPool();
        }

        nettyClientPool = new NettyClientPool(host, port, nettyClient);
        clientPoolMap.put(key, nettyClientPool);
        return nettyClientPool.getPool();
    }
}
