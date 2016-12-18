package cn.migu.plugin.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * REST服务注解
 * Created by Song on 2016/12/18.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rest {

    /**
     * 服务名
     * @return
     */
    String value() default "";
}
