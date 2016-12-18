package cn.migu.plugin.soap;


import cn.migu.framwork.helper.ConfigHelper;

/**
 * 从属性文件中获取相关属性
 * Created by Song on 2016/12/16.
 */
public class SoapConfig {

    public static boolean isLog() {
        return ConfigHelper.getBooealn(SoapConstant.LOG);
    }
}
