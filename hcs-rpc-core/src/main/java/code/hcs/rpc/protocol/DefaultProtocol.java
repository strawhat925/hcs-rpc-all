package code.hcs.rpc.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;
import code.hcs.rpc.buff.ByteBufferWapper;
import code.hcs.rpc.codec.Codecs;

/**
 * default protocol
 * package code.hcs.rpc.protocol
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 17:53
 **/
//-------------------------------------------------------------------------------------------------------
//自定义tcp协议

//## 消息格式
// protocol header
// [version 1B]  protocol version
// [type 4B] protocol type
// [type 1B] request/response type
// [codec type 4B] serialize/deserialize type

// protocol request


//-------------------------------------------------------------------------------------------------------
public class DefaultProtocol implements Protocol {
    private static final Logger logger = LoggerFactory.getLogger(DefaultProtocol.class);
    public final static int TYPE = 1;
    public final static byte VERSION = (byte) 1;
    public final static byte REQUEST = (byte) 0;
    public final static byte RESPONSE = (byte) 1;
    public final static int HEADER_LEN = 1 + 4 + 4 + 1;

    @Override
    public ByteBufferWapper encode(ByteBufferWapper byteBufferWapper, Object message) throws Exception {
        if (message instanceof RequestWapper) {
            try {
                RequestWapper requestWapper = (RequestWapper) message;
                byte[] body = Codecs.getEncoder(requestWapper.getCodecType()).encode(message);
                // apply bytebuffer capacity
                int capacity = HEADER_LEN + body.length;
                ByteBufferWapper bufferWapper = byteBufferWapper.get(capacity);

                //1b version
                bufferWapper.writeByte(VERSION);
                //4b protocol type
                bufferWapper.writeInt(requestWapper.getProtocolType());
                //1b request type
                bufferWapper.writeByte(REQUEST);
                //4b codec type
                bufferWapper.writeInt(requestWapper.getCodecType());
                System.out.println(body.length + "==========================");
                //4b body length
                bufferWapper.writeInt(body.length);
                // body
                bufferWapper.writeBytes(body);
                return bufferWapper;
            } catch (Exception e) {
                logger.error("encode request object error", e);
                throw e;
            }
        } else if (message instanceof ResponseWapper) {
            try {
                ResponseWapper responseWapper = (ResponseWapper) message;
                byte[] body = Codecs.getEncoder(responseWapper.getCodecType()).encode(message);

                int capacity = HEADER_LEN + body.length;
                ByteBufferWapper bufferWapper = byteBufferWapper.get(capacity);
                bufferWapper.writeByte(VERSION);
                bufferWapper.writeInt(responseWapper.getProtocolType());
                bufferWapper.writeByte(RESPONSE);
                bufferWapper.writeInt(responseWapper.getCodecType());
                bufferWapper.writeInt(body.length);
                bufferWapper.writeBytes(body);

                return bufferWapper;
            } catch (Exception e) {
                logger.error("encode response object error", e);
                throw e;
            }
        }
        return null;
    }

    @Override
    public Object decode(ByteBufferWapper byteBufferWapper, Object errorObject, int... originPosArray) throws Exception {
        int originPos;
        if (originPosArray != null && originPosArray.length == 1) {
            originPos = originPosArray[0];
        } else {
            originPos = byteBufferWapper.readerIndex();
        }

        if (byteBufferWapper.readableBytes() < 2) {
            byteBufferWapper.setReaderIndex(originPos);
            return errorObject;
        }
        int flag = byteBufferWapper.readByte();
        int codecType = byteBufferWapper.readInt();
        System.out.println(codecType + "==========================");
        int len = byteBufferWapper.readInt();
        System.out.println(len + "==========================");
        byte[] body = new byte[len];
        byteBufferWapper.readBytes(body);
        return Codecs.getDecoder(codecType).decode(body);
    }
}
