package demo.ws.soap_cxf;

import javax.jws.WebService;

/**
 * Created by Song on 2016/12/13.
 */
@WebService
public class HelloServiceImpl implements HelloService{

    public String say(String name) {
        return "hello" + name;
    }
}
