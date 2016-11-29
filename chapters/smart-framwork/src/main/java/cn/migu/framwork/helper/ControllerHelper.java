package cn.migu.framwork.helper;

import cn.migu.framwork.annotation.Action;
import cn.migu.framwork.bean.Handler;
import cn.migu.framwork.bean.Request;
import cn.migu.framwork.util.ArrayUtil;
import cn.migu.framwork.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * Created by Song on 2016/11/28.
 */
public final class ControllerHelper {

    //存放请求与处理器的映射关系
    private static final Map<Request, Handler> ACTION_MAP =  new HashMap<Request, Handler>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)){
            for (Class<?> controllerClass : controllerClassSet){
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)){
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                                    //获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    //初始化Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //获取Handler
    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
