package demo.ws.soap_cxf;

import javax.jws.WebService;

/**
 * Created by Song on 2016/12/13.
 */
@WebService
public interface HelloService {
    String say(String name);
}
;