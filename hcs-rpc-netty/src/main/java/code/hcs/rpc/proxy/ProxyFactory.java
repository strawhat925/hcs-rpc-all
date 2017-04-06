package code.hcs.rpc.proxy;


import java.lang.reflect.InvocationHandler;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.proxy
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 15:41
 **/
public interface ProxyFactory {

    <T> T getPorxy(Class<T> clz, InvocationHandler invocationHandler);
}
