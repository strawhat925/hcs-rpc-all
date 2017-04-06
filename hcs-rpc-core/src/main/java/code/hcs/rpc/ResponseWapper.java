package code.hcs.rpc;

import java.io.Serializable;

import code.hcs.rpc.codec.Codecs;

/**
 * package code.hcs.rpc
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-03-30 13:36
 **/
public class ResponseWapper implements Serializable{

    private int requestId = 0;

    private Object response = null;

    private boolean isError = false;

    private Throwable throwable = null;

    private int codecType = Codecs.HESSIAN_CODE;

    private int protocolType;

    private long processTime;


    public ResponseWapper(int requestId, int codecType, int protocolType) {
        this.requestId = requestId;
        this.codecType = codecType;
        this.protocolType = protocolType;
    }

    public ResponseWapper(int requestId, Object response, boolean isError, Throwable throwable, int codecType, int protocolType, long processTime) {
        this.requestId = requestId;
        this.response = response;
        this.isError = isError;
        this.throwable = throwable;
        this.codecType = codecType;
        this.protocolType = protocolType;
        this.processTime = processTime;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
        this.isError = true;
    }

    public int getCodecType() {
        return codecType;
    }

    public void setCodecType(int codecType) {
        this.codecType = codecType;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
