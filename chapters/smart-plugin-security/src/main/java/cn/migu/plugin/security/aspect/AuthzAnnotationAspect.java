package cn.migu.plugin.security.aspect;

import cn.migu.framwork.annotation.Aspect;
import cn.migu.framwork.annotation.Controller;
import cn.migu.framwork.proxy.AspectProxy;
import cn.migu.plugin.security.annotation.*;
import cn.migu.plugin.security.exception.AuthzException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 授权注解切面
 * Created by Song on 2016/12/13.
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy{

    //定义一个基于授权功能的注解类数组
    private static final Class[] ANNOTATION_CALSS_ARRAY = {User.class, Guest.class,
            Authenticated.class, HasRoles.class, HasPermissions.class};

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        //从目标类与目标方法中获取相应的注解
        Annotation annotation = getAnnotation(cls, method);
        if (annotation != null){
            Class<?> annotationType = annotation.annotationType();
            handle(annotationType, params);
        }
    }

    private void handle(Class<?> annotationType, Object[] params) {
        if (annotationType.equals(User.class)){
            handleUser(params);
        }else if (annotationType.equals(Guest.class)){
            handleGuest(params);
        }else if (annotationType.equals(Authenticated.class)){
            handleAuth(params);
        }else if (annotationType.equals(HasRoles.class)){
            handleHasRoles(params);
        }else if (annotationType.equals(HasPermissions.class)){
            handleHasPermissions(params);
        }
    }

    private void handleAuth(Object[] params) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()){
            throw new AuthzException("当前用户尚未认证");
        }
    }

    private void handleHasRoles(Object[] params) {
        Subject currentUser = SecurityUtils.getSubject();
        boolean[] isHasRoles;
        for (Object param : params){
            isHasRoles = currentUser.hasRoles((List<String>) param);
            for (boolean isHasRole : isHasRoles){
                if (isHasRole == false){
                    throw new AuthzException("当前用户未拥有所有对应角色");
                }
            }
        }
    }

    private void handleHasPermissions(Object[] params) {
        Subject currentUser = SecurityUtils.getSubject();
        for (Object param : params){
            try {
                currentUser.checkPermissions((String[]) param);
            }catch (AuthorizationException e){
                throw new AuthzException("当前用户未拥有所有对应权限", e);
            }
        }
    }

    private void handleGuest(Object[] params) {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals != null || !principals.isEmpty()){
            throw new AuthzException("当前游客已经登录");
        }
    }

    private void handleUser(Object[] params) {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()){
            throw new AuthzException("当前用户尚未登录");
        }
    }

    private Annotation getAnnotation(Class<?> cls, Method method) {
        //遍历所有的授权注解
        for (Class<? extends Annotation> annotationClass : ANNOTATION_CALSS_ARRAY){
            //首先判断目标方法上是否带有授权注解
            if (method.isAnnotationPresent(annotationClass)){
                return method.getAnnotation(annotationClass);
            }
            //判断目标类上是否带有授权注解
            if (cls.isAnnotationPresent(annotationClass)){
                return cls.getAnnotation(annotationClass);
            }
        }
        //若目标方法与目标类上都没有带有授权注解，返回空对象
        return null;
    }
}
