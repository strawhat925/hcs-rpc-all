package code.hcs.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.client.NettyClient;

/**
 * jdk动态代理，基于接口
 * package code.hcs.rpc.proxy
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 15:38
 **/
public class JdkInvocationHandler implements InvocationHandler {

    private int timeout;
    private int protocolType;
    private int codecType;
    private String targetInstanceName;
    private List<InetSocketAddress> servers;

    public JdkInvocationHandler(int timeout, int protocolType, int codecType, String targetInstanceName, List<InetSocketAddress> servers) {
        this.timeout = timeout;
        this.protocolType = protocolType;
        this.codecType = codecType;
        this.targetInstanceName = targetInstanceName;
        this.servers = Collections.unmodifiableList(servers);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InetSocketAddress server = null;
        if (servers.size() == 1) {
            server = servers.get(0);
        } else {
            Random random = new Random();
            server = servers.get(random.nextInt(servers.size()));
        }

        RequestWapper request = new RequestWapper(timeout, protocolType, codecType);
        request.setTargetInstanceName(targetInstanceName);
        request.setMethodName(method.getName());
        request.setArgTypes(prcessParameterTypes(method.getParameterTypes()));
        request.setRequestObjects(args);

        //loadbalance
        NettyClient client = new NettyClient(server.getAddress().getHostAddress(), server.getPort());
        ResponseWapper response = client.sendReqeust(request);
        if (response.isError()) {
            Throwable t = response.getThrowable();
            t.fillInStackTrace();
            String errorMsg = "server error,server is: " + server.getAddress().getHostAddress()
                    + ":" + server.getPort() + " request id is:"
                    + response.getRequestId();
            throw new Exception(errorMsg, t);
        }
        return response.getResponse();
    }


    private String[] prcessParameterTypes(Class<?>[] classes) {
        if (classes == null || classes.length == 0) {
            return null;
        }
        String[] parmeters = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            parmeters[i] = classes[i].getName();
        }

        return parmeters;
    }
}
