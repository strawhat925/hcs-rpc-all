package code.hcs.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import code.hcs.rpc.Server;
import code.hcs.rpc.handler.MessageHandler;
import code.hcs.rpc.serialize.NettyProtocolDecoder;
import code.hcs.rpc.serialize.NettyProtocolEncoder;
import code.hcs.rpc.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.server
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 13:54
 **/
public class NettyServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private ServerBootstrap bootstrap;
    private io.netty.channel.Channel channel;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private MessageHandler messageHandler;

    //processors
    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();


    public NettyServer() {

        NamedThreadFactory bossNtf = new NamedThreadFactory("netty-boss");
        NamedThreadFactory workerNtf = new NamedThreadFactory("netty-worker");
        boss = new NioEventLoopGroup(PROCESSORS, bossNtf);
        //核心分发线程
        worker = new NioEventLoopGroup(PROCESSORS * 2, workerNtf);
        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, Boolean.parseBoolean(System.getProperty("hcs.rpc.nodelay", "true")))
                .option(ChannelOption.SO_KEEPALIVE, Boolean.parseBoolean(System.getProperty("hcs.rpc.keepalive", "true")));

    }

    public synchronized void start(int listenPort, final ExecutorService executorService) throws Exception {
        try {
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            .addLast("encoder", new NettyProtocolEncoder())
                            .addLast("decoder", new NettyProtocolDecoder())
                            .addLast("handler", new NettyServerHandler(executorService, messageHandler));
                }
            });

            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(listenPort)).sync();
            this.channel = channelFuture.channel();

            logger.debug("start netty server success, port={}", listenPort);
            this.channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("start netty server exception:", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void close() {
        if (channel != null) {
            if (channel.isOpen()) {
                channel.close();
            }
        }
    }


    public void registerMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


    public static void main(String[] args) {
        System.out.println(PROCESSORS);
    }

}
