package code.hcs.rpc;


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import code.hcs.rpc.codec.Codecs;
import code.hcs.rpc.protocol.DefaultProtocol;
import code.hcs.rpc.proxy.JdkInvocationHandler;
import code.hcs.rpc.proxy.JdkProxyFactory;
import code.hcs.rpc.service.HelloWord;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 9:50
 **/
public class BenchMarkClient extends AbstractBenchMarkClient {


    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("must give two args: host | port");
        }

        String targetIP = args[0];
        int targetPort = Integer.parseInt(args[1]);
        List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
        servers.add(new InetSocketAddress(targetIP, targetPort));
        HelloWord helloWord = new BenchMarkClient().getProxyInstance("helloWorld", 1000, DefaultProtocol.TYPE, Codecs.HESSIAN_CODE, servers);
        String name = helloWord.sysHello("xxx");
        System.out.println(name);
    }

    @Override
    public <T> T getProxyInstance(String serviceName, int timeout, int protocolType, int codecType, List<InetSocketAddress> servers) {
        return (T) new JdkProxyFactory().getPorxy(HelloWord.class, new JdkInvocationHandler(timeout, protocolType, codecType, serviceName, servers));
    }
}
