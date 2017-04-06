package code.hcs.rpc.codec;


/**
 * define encoder inter
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 10:55
 **/
public interface Encoder {

    public byte[] encode(Object value) throws Exception;
}
