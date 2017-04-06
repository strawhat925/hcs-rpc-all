package code.hcs.rpc.protocol;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.buff.ByteBufferWapper;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.protocol
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 17:49
 **/
public class ProcotolUtil {

    public static ByteBufferWapper encode(ByteBufferWapper byteBufferWapper, Object message) throws Exception {
        Integer type = 0;
        if (message instanceof RequestWapper) {
            type = ((RequestWapper) message).getProtocolType();
            System.out.println(type);
        } else if (message instanceof ResponseWapper) {
            type = ((ResponseWapper) message).getProtocolType();
        }
        return ProtocolFactory.getProtocol(type).encode(byteBufferWapper, message);
    }


    public static Object decode(ByteBufferWapper byteBufferWapper, Object errorObject) throws Exception {
        int originPos = byteBufferWapper.readerIndex();
        if (byteBufferWapper.readableBytes() < 2) {
            byteBufferWapper.setReaderIndex(originPos);
            return errorObject;
        }

        int version = byteBufferWapper.readByte();
        if (version == 1) {
            int type = byteBufferWapper.readInt();
            Protocol protocol = ProtocolFactory.getProtocol(type);
            if (protocol == null) {
                throw new Exception("Unsupport protocol type:" + type);
            }
            return ProtocolFactory.getProtocol(type).decode(byteBufferWapper, errorObject, originPos);
        } else {
            throw new Exception("Unsupport protocol version:" + version);
        }

    }
}
