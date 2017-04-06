package code.hcs.rpc.buff;


/**
 * custom buffer
 * ${@Link netty ByteBuff}
 * package code.hcs.rpc.buff
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 17:21
 **/
public interface ByteBufferWapper {

    public ByteBufferWapper get(int capacity);

    public void writeByte(int index,byte data);

    public void writeByte(byte data);

    public byte readByte();

    public void writeInt(int data);

    public void writeBytes(byte[] data);

    public int readableBytes();

    public int readInt();

    public void readBytes(byte[] dst);

    public int readerIndex();

    public void setReaderIndex(int readerIndex);
}
