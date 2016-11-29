package cn.migu.chapter2.util;

import org.apache.commons.lang3.StringUtils;

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
}
