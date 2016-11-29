package cn.migu.framwork.util;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.naming.Name;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * Created by Song on 2016/11/27.
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类，这里的初始化指是否执行类内的静态代码块
     */
    public static Class<?> loadClass(String className){
        boolean isInitialized = false;
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className, boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url != null){
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")){
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packageName,packagePath);
                    }else if (protocol.equals("jar")){
                        JarURLConnection jarURLConn = (JarURLConnection) url.openConnection();
                        if (jarURLConn != null){
                            JarFile jarFile = jarURLConn.getJarFile();
                            if (jarFile != null){
                                Enumeration<JarEntry> jarEnties = jarFile.entries();
                                while (jarEnties.hasMoreElements()){
                                    JarEntry jarEntry = jarEnties.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() & file.getName().endsWith("class")) || file.isDirectory();
            }
        });
        for (File file : files){
            String fileName = file.getName();
            if (file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            }else{
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
