package cn.migu.framwork.util;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

/**
 * 数组工具类
 * Created by Song on 2016/11/28.
 */
public final class ArrayUtil {

    public static boolean isNotEmpty(Object[] arr) {
        return !ArrayUtils.isEmpty(arr);
    }

    public static boolean isEmpty(Object[] arr) {
        return ArrayUtils.isEmpty(arr);
    }
}
