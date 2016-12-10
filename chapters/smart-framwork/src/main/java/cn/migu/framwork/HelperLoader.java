package cn.migu.framwork;

import cn.migu.framwork.annotation.Controller;
import cn.migu.framwork.helper.*;
import cn.migu.framwork.util.ClassUtil;

/**
 * 加载相应的Helper类
 * Created by Song on 2016/11/28.
 */
public final class HelperLoader {
    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
