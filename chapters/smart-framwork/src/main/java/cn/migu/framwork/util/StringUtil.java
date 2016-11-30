package cn.migu.framwork.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Song on 2016/11/23.
 */
public final class StringUtil {

    /**
     * 判断字符串是否为空
     * @param strValue
     * @return
     */
    public static boolean isEmpty(String strValue){
        if(strValue != null){
            strValue = strValue.trim();
        }
        return StringUtils.isEmpty(strValue);
    }

    /**
     *判断字符串是否非空
     * @param strValue
     * @return
     */
    public static boolean isNotEmpty(String strValue) {
        return !isEmpty(strValue);
    }

    /**
     * 切割body
     * @param body
     * @param s
     * @return
     */
    public static String[] splitString(String body, String s) {
        String[] arr = null;
        if (!"".equals(body)  && body != null){
            arr = body.split(s);
        }
        return arr;
    }
}
