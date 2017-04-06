package code.hcs.rpc.handler;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * package code.hcs.rpc.handler
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-01 10:23
 **/
public abstract class AbstartMessageHandler implements MessageHandler{
    //cache service map key:service name value:service instance
    protected Map<String, Object> processors = Maps.newHashMap();

    //cache method map  key:instancename#methodname$argstype_argstype
    protected Map<String, Method> cacheMethod = Maps.newHashMap();

    @Override
    public void registerProcessor(String instanceName, Object instance){
        Object processor = processors.get(instanceName);
        if(processor != null){
            return;
        }
        processors.put(instanceName, instance);
        Class<?> instanceClass = instance.getClass();
        Method[] methods = instanceClass.getMethods();
        for (Method method : methods) {
            StringBuilder sb = new StringBuilder();
            sb.append(instanceName).append("#");
            sb.append(method.getName()).append("$");
            Class<?>[] argTypes = method.getParameterTypes();
            for (Class argClass : argTypes) {
                sb.append(argClass.getName()).append("_");
            }
            cacheMethod.put(sb.toString(), method);
        }
    }
}
