package cn.migu.framwork;

import cn.migu.framwork.bean.Data;
import cn.migu.framwork.bean.Handler;
import cn.migu.framwork.bean.Param;
import cn.migu.framwork.bean.View;
import cn.migu.framwork.helper.*;
import cn.migu.framwork.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

/**
 * 请求转发器
 * Created by Song on 2016/11/30.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化helper类
        HelperLoader.init();
        //获取ServletContext对象
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServletHelper.init(req, resp);
        try {
            //获取请求方法与请求路径
            String reqMethod = req.getMethod().toLowerCase();
            String reqPath = req.getPathInfo();

            if (reqPath.equals("/favicon.ico")){
                return;
            }

            //获取Action处理器
            Handler handler = ControllerHelper.getHandler(reqMethod, reqPath);
            if (handler != null){
                //获取Controller类以及Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param = null;
                if (UploadHelper.isMultipart(req)){
                    param = UploadHelper.createParam(req);
                }else {
                    try {
                        param = RequestHelper.createParam(req);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //调用Action方法
                Method actionMethod = handler.getActionMethod();
                Object result;
                if (param.isEmpty()){
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                }else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理Action方法返回值
                if (result instanceof View){
                    //返回JSP页面
                    handleViewResult((View)result, req, resp);
                }else if (result instanceof Data){
                    //返回JSON数据
                    handleDataResult((Data)result, req, resp);
                }
            }
        }finally {
            ServletHelper.destory(req, resp);
        }

    }

    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //返回JSP页面
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)){
            if (path.startsWith("/")){
                resp.sendRedirect(req.getContextPath() + path);
            }else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()){
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //返回JSON数据
        Object model = data.getModel();
        if (model != null){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
