package code.hcs.rpc.client;

import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.RpcCallbackFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.client
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:58
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<ResponseWapper> {
    public NettyClientHandler(NettyClient client){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseWapper responseWapper) throws Exception {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        RpcCallbackFuture future = RpcCallbackFuture.futurePool.get(responseWapper.getRequestId());
        future.setResponse(responseWapper);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
