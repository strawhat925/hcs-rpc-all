package code.hcs.rpc.codec;

import com.caucho.hessian.io.Hessian2Input;

import java.io.ByteArrayInputStream;

/**
 * hessian serialize
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 11:11
 **/
public class HessianDecoder implements Decoder {

    @Override
    public Object decode(byte[] value) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(value);
        Hessian2Input in = new Hessian2Input(bis);
        Object resultObject = in.readObject();
        in.close();
        return resultObject;
    }
}
