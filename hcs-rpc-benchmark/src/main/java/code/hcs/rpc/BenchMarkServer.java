package code.hcs.rpc;


import code.hcs.rpc.server.NettyServer;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 16:05
 **/
public class BenchMarkServer extends Application {


    public static void main(String[] args) throws Exception {
        new BenchMarkServer().run(args);
    }

    @Override
    public Server getServer() {
        return new NettyServer();
    }
}
