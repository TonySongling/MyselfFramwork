package cn.migu.plugin.soap;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * SOAP助手类
 * Created by Song on 2016/12/16.
 */
public final class SoapHelper {
    private static final List<Interceptor<? extends Message>> inInterceptorList
            = new ArrayList<Interceptor<? extends Message>>();
    private static final List<Interceptor<? extends Message>> outInterceptorList
            = new ArrayList<Interceptor<? extends Message>>();

    static {
        //添加Logging Interceptor
        if(SoapConfig.isLog()){
            LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            inInterceptorList.add(loggingInInterceptor);
            LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            outInterceptorList.add(loggingOutInterceptor);
        }
    }

    /**
     * 发布SOAP服务
     * @param wsdl
     * @param interfaceClass
     * @param implementInstance
     */
    public static void publishService(String wsdl, Class<?> interfaceClass, Object implementInstance){
        ServerFactoryBean factoryBean = new ServerFactoryBean();
        factoryBean.setAddress(wsdl);
        factoryBean.setServiceClass(interfaceClass);
        factoryBean.setServiceBean(implementInstance);
        factoryBean.setInInterceptors(inInterceptorList);
        factoryBean.setOutInterceptors(outInterceptorList);
        factoryBean.create();
    }

    /**
     * 穿件SOAP客户端
     * @param wsdl
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public static <T> T createClient(String wsdl, Class<? extends T> interfaceClass){
        ClientProxyFactoryBean factoryBean = new ClientProxyFactoryBean();
        factoryBean.setAddress(wsdl);
        factoryBean.setServiceClass(interfaceClass);
        factoryBean.setInInterceptors(inInterceptorList);
        factoryBean.setOutInterceptors(outInterceptorList);
        return factoryBean.create(interfaceClass);
    }
}
