package cn.migu.plugin.rest;

import cn.migu.framwork.helper.ClassHelper;
import cn.migu.framwork.util.CollectionUtil;
import cn.migu.framwork.util.StringUtil;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import javax.jws.WebService;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import java.util.Set;

/**
 * Created by Song on 2016/12/18.
 */
@WebServlet(urlPatterns = RestConstant.SERVLET_URL, loadOnStartup = 0)
public class RestServlet extends CXFNonSpringServlet{

    @Override
    protected void loadBus(ServletConfig sc) {
        //初始化CXF总线
        super.loadBus(sc);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        //发布REST服务
        publishRestService();
    }

    private void publishRestService() {
        //遍历所有标注REST注解的类
        Set<Class<?>> restClassSet = ClassHelper.getClassSetByAnnotation(Rest.class);
        if (CollectionUtil.isNotEmpty(restClassSet)){
            for (Class<?> restClass : restClassSet){
                //获取REST地址
                String address = getAdress(restClass);
                //发布REST服务
                RestHelper.publishService(address, restClass);
            }
        }
    }

    private String getAdress(Class<?> restClass) {
        String address;
        //若REST注解的value属性不为空，则获取当前值，否则获取类名
        String value = restClass.getAnnotation(Rest.class).value();
        if (StringUtil.isNotEmpty(value)){
            address = value;
        }else {
            address = restClass.getSimpleName();
        }

        if (!address.startsWith("/")){
            address = "/" + address;
        }

        address = address.replaceAll("\\/+", "/");
        return address;
    }
}
