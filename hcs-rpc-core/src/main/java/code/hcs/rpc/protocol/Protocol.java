package code.hcs.rpc.protocol;


import code.hcs.rpc.buff.ByteBufferWapper;

/**
 * custom network protocol
 * package code.hcs.rpc.protocol
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 11:40
 **/
public interface Protocol {


    /**
     * encode Message to byte & write to network framework
     *
     * @param byteBufferWapper
     * @param message
     *
     * @return
     *
     * @throws Exception
     */
    public ByteBufferWapper encode(ByteBufferWapper byteBufferWapper, Object message) throws Exception;


    /**
     * decode stream to object
     *
     * @param byteBufferWapper
     * @param errorObject
     * @param originPos
     *
     * @return
     *
     * @throws Exception
     */
    public Object decode(ByteBufferWapper byteBufferWapper, Object errorObject, int... originPos) throws Exception;
}
