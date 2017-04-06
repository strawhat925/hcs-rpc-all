package code.hcs.rpc;

/**
 * Created by Administrator on 2016/12/28.
 */
public class Constants {
    public static final String SPLIT = ",";
    //魔数
    public static final short MAGIC = (short) 0xF0F0;
    //标记位
    public static final int FLAG_REQUEST = 0x01;

    public static final int FLAG_RESPONSE = 0x02;

    public static final int FLAG_RESPONSE_EXCEPTION = 0x03;

    public static final int FLAG_RESPONSE_VOID = 0x04;


}
