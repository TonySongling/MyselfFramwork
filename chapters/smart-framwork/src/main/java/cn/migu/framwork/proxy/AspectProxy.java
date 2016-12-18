package cn.migu.framwork.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * Created by Song on 2016/12/4.
 */
public abstract class AspectProxy implements Proxy{
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)){
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
        }finally {
            end();
        }
        return result;
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable{
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
        return true;
    }

    public void begin(){

    }

    public void end(){

    }
}
