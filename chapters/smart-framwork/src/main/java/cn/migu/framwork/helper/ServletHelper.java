package cn.migu.framwork.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet助手类
 * Created by Song on 2016/12/9.
 */
public final class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     * @param request
     * @param response
     */
    public static void destory(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 获取request对象
     * @return
     */
    private static HttpServletRequest getRequest(){
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * 获取response对象
     * @return
     */
    private static HttpServletResponse getResponse(){
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * 获取Session对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * 将属性设入Request中
     * @param key
     * @param value
     */
    public static void setRequestAttrubute(String key, Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * 获取属性值
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getRequestAttribute(String key){
        return (T)getRequest().getAttribute(key);
    }

    /**
     * 移除属性
     * @param key
     */
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     * @param location
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("redirect failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将属性设置进去Session
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * 从Session中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getSessionAttribute(String key){
        return (T)getSession().getAttribute(key);
    }

    /**
     * 从session中移除属性
     * @param key
     */
    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }

    /**
     * 是session失效
     */
    public static void invalidateSession(){
        getSession().invalidate();
    }
 }
