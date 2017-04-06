package code.hcs.rpc.codec;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * java serialize decode
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 11:05
 **/
public class JavaDecoder implements Decoder {

    @Override
    public Object decode(byte[] value) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(value);
        ObjectInputStream in = new ObjectInputStream(bis);
        Object resultObject = in.readObject();
        in.close();

        return resultObject;
    }
}
