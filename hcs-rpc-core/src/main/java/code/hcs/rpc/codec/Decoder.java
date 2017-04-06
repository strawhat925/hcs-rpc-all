package code.hcs.rpc.codec;


/**
 * define decoder inter
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 10:55
 **/
public interface Decoder {

    public Object decode(byte[] value) throws Exception;
}
