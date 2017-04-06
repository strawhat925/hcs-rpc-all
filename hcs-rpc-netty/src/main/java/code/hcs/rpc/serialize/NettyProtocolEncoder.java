package code.hcs.rpc.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.hcs.rpc.buff.NettyByteBufferWapper;
import code.hcs.rpc.protocol.ProcotolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * package code.hcs.rpc.serialize
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 16:32
 **/
public class NettyProtocolEncoder extends MessageToByteEncoder<Object> {
    private static final Logger logger = LoggerFactory.getLogger(NettyProtocolEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        System.out.println("----------------------------encoder---------------------------");
        NettyByteBufferWapper nettyByteBufferWapper = new NettyByteBufferWapper(byteBuf);
        ProcotolUtil.encode(nettyByteBufferWapper, o);
        logger.debug("netty procotol encode is:{}", o);
    }
}
