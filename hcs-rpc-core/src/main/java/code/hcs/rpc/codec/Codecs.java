package code.hcs.rpc.codec;

/**
 * codec{@Link #Encoder #Decoder } used to obtain serialization types
 * <pre>
 *      package code.hcs.rpc.codec
 * </pre>
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 11:13
 **/
public final class Codecs {

    public static final int JAVA_CODE = 1;
    public static final int HESSIAN_CODE = 2;


    private static Encoder[] encoders = new Encoder[3];
    private static Decoder[] decoders = new Decoder[3];

    static {
        addEncoder(JAVA_CODE, new JavaEncoder());
        addEncoder(HESSIAN_CODE, new HessianEncoder());

        addDecoder(JAVA_CODE, new JavaDecoder());
        addDecoder(HESSIAN_CODE, new HessianDecoder());
    }

    public static void addEncoder(int encodeKey, Encoder encoder) {
        if (encodeKey > encoders.length) {
            Encoder[] newEncoders = new Encoder[encodeKey + 1];
            System.arraycopy(encoders, 0, newEncoders, 0, encoders.length);
            encoders = newEncoders;
        }
        encoders[encodeKey] = encoder;
    }


    public static void addDecoder(int decodeKey, Decoder decoder) {
        if (decodeKey > decoders.length) {
            Decoder[] newDecoders = new Decoder[decodeKey + 1];
            System.arraycopy(decoders, 0, newDecoders, 0, decoders.length);
            decoders = newDecoders;
        }
        decoders[decodeKey] = decoder;
    }


    public static Encoder getEncoder(int encodeKey) {
        return encoders[encodeKey];
    }

    public static Decoder getDecoder(int decodeKey) {
        return decoders[decodeKey];
    }


    public static void main(String[] args) {

    }
}
