package code.hcs.rpc;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import code.hcs.rpc.codec.Codecs;

/**
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 13:35
 **/
public class RequestWapper implements Serializable {

    private static AtomicInteger incId = new AtomicInteger(1);

    private String targetInstanceName;

    private String methodName;

    private String[] argTypes;

    private Object[] requestObjects = null;

    private int timeout = 0;

    private int id = 0;

    private int protocolType;

    private int codecType = Codecs.HESSIAN_CODE;

    public RequestWapper(int timeout, int protocolType, int codecType) {
        this(timeout, incId.getAndIncrement(), protocolType, codecType);
    }

    public RequestWapper(int timeout, int id, int protocolType, int codecType) {
        this.timeout = timeout;
        this.id = id;
        this.protocolType = protocolType;
        this.codecType = codecType;
    }

    public static AtomicInteger getIncId() {
        return incId;
    }

    public static void setIncId(AtomicInteger incId) {
        RequestWapper.incId = incId;
    }

    public String getTargetInstanceName() {
        return targetInstanceName;
    }

    public void setTargetInstanceName(String targetInstanceName) {
        this.targetInstanceName = targetInstanceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(String[] argTypes) {
        this.argTypes = argTypes;
    }

    public Object[] getRequestObjects() {
        return requestObjects;
    }

    public void setRequestObjects(Object[] requestObjects) {
        this.requestObjects = requestObjects;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public int getCodecType() {
        return codecType;
    }

    public void setCodecType(int codecType) {
        this.codecType = codecType;
    }

}
