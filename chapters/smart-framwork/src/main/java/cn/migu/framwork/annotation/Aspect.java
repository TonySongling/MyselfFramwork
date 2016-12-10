package cn.migu.framwork.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * Created by Song on 2016/12/4.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
