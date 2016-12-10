package cn.migu.framwork.proxy;

/**
 * 代理接口
 * Created by Song on 2016/12/4.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
