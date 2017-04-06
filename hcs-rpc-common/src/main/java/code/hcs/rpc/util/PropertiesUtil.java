package code.hcs.rpc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * package code.hcs.rpc.util
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 15:23
 **/
public final class PropertiesUtil {

    private static Properties props;
    private static String fileName = PropertiesUtil.class.getResource("/").getPath() + "netty-pool.properties";

    static {
        load(fileName);
    }

    private static void load(String fileName) {
        try {
            props = new Properties();
            FileInputStream fis = new FileInputStream(fileName);
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个属性
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public static Map getAllProperty() {
        Map map = new HashMap();
        Enumeration enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public static void printProperties() {
        props.list(System.out);
    }

    /**
     * 写入properties信息
     */
    public static void writeProperties(String key, String value) {
        try {
            OutputStream fos = new FileOutputStream(fileName);
            props.setProperty(key, value);
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "『comments』Update key：" + key);
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
    }
}