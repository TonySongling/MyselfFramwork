package cn.migu.framwork.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by Song on 2016/11/28.
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result = null;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}
