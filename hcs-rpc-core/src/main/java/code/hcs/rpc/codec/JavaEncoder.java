package code.hcs.rpc.codec;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * java serialize encode
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 10:58
 **/
public class JavaEncoder implements Encoder {

    @Override
    public byte[] encode(Object value) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(value);
        out.flush();
        out.close();
        return bos.toByteArray();
    }
}
