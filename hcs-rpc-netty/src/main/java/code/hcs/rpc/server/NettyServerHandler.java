package code.hcs.rpc.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.handler.MessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * server handler
 * package code.hcs.rpc.server
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 14:27
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<RequestWapper> {
    private static ExecutorService executorService;
    private MessageHandler messageHandler;

    public NettyServerHandler(ExecutorService executorService, MessageHandler messageHandler) {
        this.executorService = executorService;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final RequestWapper requestWapper) throws Exception {
        final long processStartTime = System.currentTimeMillis();
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handleRequest(ctx, requestWapper, processStartTime);
                }
            });
        } catch (RejectedExecutionException e) {

        }
    }


    private void handleRequest(ChannelHandlerContext ctx, RequestWapper requestWapper, long processStartTime) {
        if (messageHandler == null) {
            //
            return;
        }
        ResponseWapper responseWapper = messageHandler.handleRequest(requestWapper);
        responseWapper.setProcessTime(System.currentTimeMillis() - processStartTime);
        ctx.writeAndFlush(responseWapper);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
