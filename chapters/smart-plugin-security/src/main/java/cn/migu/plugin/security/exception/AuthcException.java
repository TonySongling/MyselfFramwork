package cn.migu.plugin.security.exception;

/**
 * 认证异常
 * Created by Song on 2016/12/11.
 */
public class AuthcException extends Exception {

    public AuthcException() {
        super();
    }

    public AuthcException(String message){
        super(message);
    }

    public AuthcException(String message, Throwable cause){
        super(message, cause);
    }

    public AuthcException(Throwable cause){
        super(cause);
    }
}
