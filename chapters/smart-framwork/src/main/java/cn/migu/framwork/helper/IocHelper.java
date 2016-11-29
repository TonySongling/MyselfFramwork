package cn.migu.framwork.helper;

import cn.migu.framwork.annotation.Inject;
import cn.migu.framwork.util.CollectionUtil;
import cn.migu.framwork.util.ArrayUtil;
import cn.migu.framwork.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by Song on 2016/11/28.
 */
public final class IocHelper {
    static {
        //获取所有Bean类与Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)){
                    for (Field field : beanFields){
                        if (field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstace = beanMap.get(beanFieldClass);
                            if (beanFieldInstace != null){
                                //反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstace);
                            }
                        }
                    }
                }
            }
        }
    }
}
