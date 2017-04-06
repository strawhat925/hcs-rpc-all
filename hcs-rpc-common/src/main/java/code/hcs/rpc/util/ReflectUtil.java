package code.hcs.rpc.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import code.hcs.rpc.Constants;

/**
 * Created by Administrator on 2016/12/28.
 */
public class ReflectUtil {

    private static final ConcurrentMap<String, Class<?>> name2ClassCache = new ConcurrentHashMap<String, Class<?>>();
    private static final ConcurrentHashMap<Class<?>, String> class2NameCache = new ConcurrentHashMap<Class<?>, String>();
    private static final Class[] NATIVE_CLASS = new Class[]{boolean.class, int.class, long.class, short.class, double.class, char.class, byte.class};
    private static final String[] NATIVE_NAME = new String[]{"boolean", "int", "long", "short", "double", "char", "byte"};
    private static final int NATIVE_CLASS_LENGTH = 7;
    private static final Class<?>[] DEFAULT_CLASS = new Class<?>[0];

    private static final String EMPTY_PARAM = "void";
    public static final String PARAM_CLASS_SPLIT = ",";

    public static Class<?>[] forNames(String className) throws ClassNotFoundException {
        if (StringUtils.isEmpty(className)) {
            return DEFAULT_CLASS;
        }
        String[] classNames = className.split(Constants.SPLIT);
        Class<?> clazz[] = new Class<?>[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            clazz[i] = forName(classNames[i]);
        }

        return clazz;

    }


    public static Class<?> forName(String className) throws ClassNotFoundException {
        if (StringUtils.isEmpty(className)) {
            return null;
        }

        Class<?> clazz = name2ClassCache.get(className);
        if (clazz != null) {
            return clazz;
        }

        clazz = forNameWithOutCache(className);
        name2ClassCache.putIfAbsent(className, clazz);
        return clazz;
    }


    public static Class<?> forNameWithOutCache(String className) throws ClassNotFoundException {
        if (!className.endsWith("[]")) {
            Class<?> clazz = getNativeClass(className);

            return (clazz != null) ? clazz : Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        }

        int dimensionSiz = 0;

        while (className.endsWith("[]")) {
            dimensionSiz++;
            className = className.substring(0, className.length() - 2);
        }

        int[] dimensions = new int[dimensionSiz];
        Class<?> clz = getNativeClass(className);
        if (clz == null) {
            clz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        }

        return Array.newInstance(clz, dimensions).getClass();
    }


    public static Class<?> getNativeClass(String className) {
        if (className.length() <= NATIVE_CLASS_LENGTH) {
            int index = Arrays.binarySearch(NATIVE_NAME, className);
            if (index >= 0) {
                return NATIVE_CLASS[index];
            }
        }
        return null;
    }

    public static String getParameterDesc(Method method) {
        if (method.getParameterTypes() == null || method.getParameterTypes().length == 0) {
            return EMPTY_PARAM;
        }

        Class<?>[] clzs = method.getParameterTypes();
        StringBuilder sb = new StringBuilder();
        for (Class clz : clzs) {
            String className = getName(clz);
            sb.append(className).append(PARAM_CLASS_SPLIT);
        }

        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 需要支持一维数组、二维数组等
     */
    public static String getName(Class<?> clz) {
        if (clz == null) {
            return null;
        }

        String className = class2NameCache.get(clz);

        if (className != null) {
            return className;
        }

        className = getNameWithoutCache(clz);

        // 与name2ClassCache同样道理，如果没有恶心的代码，这块内存大小应该可控
        class2NameCache.putIfAbsent(clz, className);

        return className;
    }

    private static String getNameWithoutCache(Class<?> clz) {
        if (!clz.isArray()) {
            return clz.getName();
        }

        StringBuilder sb = new StringBuilder();
        while (clz.isArray()) {
            sb.append("[]");
            clz = clz.getComponentType();
        }

        return clz.getName() + sb.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        List list = new ArrayList();
        System.out.println(list.getClass().getName());
        //native class
        Class<?> clazz = ReflectUtil.getNativeClass("int");
        System.out.println(clazz);

        String className = "java.lang.Object[]";
        Class<?> clazz1 = ReflectUtil.forNameWithOutCache(className);
        System.out.println(clazz1);

    }


}
