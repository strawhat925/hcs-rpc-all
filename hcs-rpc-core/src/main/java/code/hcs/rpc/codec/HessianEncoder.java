package code.hcs.rpc.codec;

import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayOutputStream;

/**
 * hessian serialize
 * package code.hcs.rpc.codec
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 11:08
 **/
public class HessianEncoder implements Encoder {

    @Override
    public byte[] encode(Object value) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(value);
        out.flush();
        out.close();
        return bos.toByteArray();
    }
}
