package code.hcs.rpc.service;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-05 15:23
 **/
public class HelloWordImpl implements HelloWord {

    @Override
    public String sysHello(String name) throws Exception {
        return "jack" + "-" + name;
    }
}
