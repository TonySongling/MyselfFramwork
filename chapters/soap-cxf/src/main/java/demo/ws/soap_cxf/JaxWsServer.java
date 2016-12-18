package demo.ws.soap_cxf;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * Created by Song on 2016/12/13.
 */
public class JaxWsServer {
    public static void main(String[] args){
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setAddress("http://localhost:8080/ws/soap/hello");
        factoryBean.setServiceClass(HelloService.class);
        factoryBean.setServiceBean(new HelloServiceImpl());
        factoryBean.create();

        System.out.println("soap ws is published");
    }
}
