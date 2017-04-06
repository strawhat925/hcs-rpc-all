package code.hcs.rpc;

import com.google.common.collect.Maps;

import java.text.MessageFormat;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 16:26
 **/
public class RpcCallbackFuture {
    public static Map<Integer, RpcCallbackFuture> futurePool = Maps.newConcurrentMap();
    private RequestWapper request;
    private ResponseWapper response;
    private boolean isDone = false;

    private Object lock = new Object();

    public RpcCallbackFuture(RequestWapper request) {
        this.request = request;
        futurePool.put(request.getId(), this);
    }


    public ResponseWapper get(long timoutMillis) throws Exception {
        if (!isDone) {
            synchronized (lock) {
                try {
                    lock.wait(timoutMillis);
                } catch (InterruptedException e) {
                    throw new Exception("response timeout:" + timoutMillis, e);
                }
            }
        }

        if (!isDone) {
            throw new Exception(MessageFormat.format("rpc, netty request timeout at:{0}, request:{1}", System.currentTimeMillis(), request.toString()));
        }
        return response;
    }


    public void setResponse(ResponseWapper response) {
        this.response = response;
        synchronized (lock) {
            this.isDone = true;
            lock.notifyAll();
        }
    }
}
