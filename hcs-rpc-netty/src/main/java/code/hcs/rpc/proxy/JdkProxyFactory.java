package code.hcs.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.proxy
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 15:58
 **/
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public <T> T getPorxy(Class<T> clz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{ clz }, invocationHandler);
    }
}
