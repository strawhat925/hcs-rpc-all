package code.hcs.rpc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import code.hcs.rpc.RequestWapper;
import code.hcs.rpc.ResponseWapper;

/**
 * default message quest type
 * package code.hcs.rpc.handler
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 9:54
 **/
public class DefaultMessageHandler extends AbstartMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageHandler.class);

    public static final int TYPE = 1;

    @Override
    public ResponseWapper handleRequest(RequestWapper requestWapper) {
        System.out.println("+++++++++++++++++++++++++++++++=" + this.processors.toString());
        ResponseWapper responseWapper = new ResponseWapper(requestWapper.getId(), requestWapper.getCodecType(), requestWapper.getProtocolType());
        Object instance = this.processors.get(requestWapper.getTargetInstanceName());
        if (instance == null) {
            responseWapper.setThrowable(new Exception("call instance does not exists in cache."));
            return responseWapper;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(requestWapper.getTargetInstanceName()).append("#");
        sb.append(requestWapper.getMethodName()).append("$");
        if (requestWapper.getArgTypes().length > 0) {
            for (int i = 0; i < requestWapper.getArgTypes().length; i++) {
                sb.append(requestWapper.getArgTypes()[i]).append("_");
            }
        }
        Method method = this.cacheMethod.get(sb.toString());
        if (method == null) {
            responseWapper.setThrowable(new Exception("call instance method does not exists in cache."));
        }

        try {
            Object value = method.invoke(instance, requestWapper.getRequestObjects());
            responseWapper.setResponse(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            responseWapper.setThrowable(e);
        }
        return responseWapper;
    }
}
