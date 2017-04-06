package code.hcs.rpc.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import code.hcs.rpc.buff.NettyByteBufferWapper;
import code.hcs.rpc.protocol.ProcotolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.serialize
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 16:33
 **/
public class NettyProtocolDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(NettyProtocolDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("----------------------------decoder---------------------------");
        NettyByteBufferWapper nettyByteBufferWapper = new NettyByteBufferWapper(byteBuf);
        Object value = ProcotolUtil.decode(nettyByteBufferWapper, null);
        logger.debug("netty procotol decode is:{}", value);
        if (value != null) {
            list.add(value);
        }
    }
}
