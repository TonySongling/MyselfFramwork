package cn.migu.plugin.security.realm;

import cn.migu.framwork.helper.DatabaseHelper;
import cn.migu.plugin.security.SecurityConfig;
import cn.migu.plugin.security.password.Md5CredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * 基于Smart的JDBC Realm（需要提供相关smart.plugin.security.jdbc.*配置项）
 * Created by Song on 2016/12/10.
 */
public class SmartJdbcRealm extends JdbcRealm{
    public SmartJdbcRealm(){
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermisssionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }
}
