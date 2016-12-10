package cn.migu.plugin.security;

import cn.migu.framwork.helper.ConfigHelper;
import cn.migu.framwork.util.ReflectionUtil;

/**
 * 从配置文件中获取相关属性
 * Created by Song on 2016/12/10.
 */
public final class SecurityConfig {

    public static String getRealms(){
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static SmartSecurity getSmartSecurity() throws ClassNotFoundException {
        String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
        return (SmartSecurity)ReflectionUtil.newInstance(Class.forName(className));
    }

    public static String getJdbcAuthcQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermisssionsQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCache() {
        return ConfigHelper.getBooealn(SecurityConstant.CACHE);
    }
}
