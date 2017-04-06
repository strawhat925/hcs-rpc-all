package code.hcs.rpc.buff;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Netty ByteBuf encapsulation
 * package code.hcs.rpc.buff
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 17:26
 **/
public class NettyByteBufferWapper implements ByteBufferWapper {

    private ByteBuf byteBuf;
    private ChannelHandlerContext ctx;

    public NettyByteBufferWapper(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public NettyByteBufferWapper(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ByteBufferWapper get(int capacity) {
        if (this.byteBuf != null) {
            return this;
        }
        this.byteBuf = this.ctx.alloc().buffer(capacity);
        return this;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public void writeByte(int index, byte data) {
        this.byteBuf.writeByte(data);
    }

    @Override
    public void writeByte(byte data) {
        this.byteBuf.writeByte(data);
    }

    @Override
    public byte readByte() {
        return this.byteBuf.readByte();
    }

    @Override
    public void writeInt(int data) {
        this.byteBuf.writeInt(data);
    }

    @Override
    public void writeBytes(byte[] data) {
        this.byteBuf.writeBytes(data);
    }

    @Override
    public int readableBytes() {
        return this.byteBuf.readableBytes();
    }

    @Override
    public int readInt() {
        return this.byteBuf.readInt();
    }

    @Override
    public void readBytes(byte[] dst) {
        this.byteBuf.readBytes(dst);
    }

    @Override
    public int readerIndex() {
        return this.byteBuf.readerIndex();
    }

    @Override
    public void setReaderIndex(int readerIndex) {
        this.byteBuf.resetReaderIndex();
    }
}
