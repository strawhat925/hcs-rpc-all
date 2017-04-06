package code.hcs.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.serialize.NettyProtocolDecoder;
import code.hcs.rpc.serialize.NettyProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * create proxy
 * package code.hcs.rpc.client
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:10
 **/
public class NettyClientPoolProxy {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientPoolProxy.class);
    private Channel channel;

    public void createProxy(String host, int port, final NettyClient nettyClient) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .option(ChannelOption.TCP_NODELAY, Boolean.parseBoolean(System.getProperty("hcs.rpc.nodelay", "true")))
                .option(ChannelOption.SO_KEEPALIVE, Boolean.parseBoolean(System.getProperty("hcs.rpc.keepalive", "true")))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast("decoder", new NettyProtocolDecoder())
                                .addLast("encoder", new NettyProtocolEncoder())
                                .addLast("handler", new NettyClientHandler(nettyClient));
                    }
                });
        channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
        logger.debug("start netty client success. host={}, port={}", host, port);
    }


    public void sendRequest(RequestWapper requestWapper) {
        this.channel.writeAndFlush(requestWapper);
    }


    public void close() {
        if (channel != null) {
            if (channel.isOpen()) {
                channel.close();
            }
        }
    }


    public boolean isValidate() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

}
