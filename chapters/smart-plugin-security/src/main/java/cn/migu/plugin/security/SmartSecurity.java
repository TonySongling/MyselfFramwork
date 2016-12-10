package cn.migu.plugin.security;

import java.util.Set;

/**
 * Created by Song on 2016/12/10.
 */
public interface SmartSecurity {

    /**
     * 根据用户名获取密码
     * @param username
     * @return
     */
    String getPassowrd(String username);

    /**
     * 根据用户名获取用户角色集合
     * @param username
     * @return
     */
    Set<String> getRoleNameSet(String username);

    /**
     * 根据角色名获取权限集合
     * @param roleName
     * @return
     */
    Set<String> getPermissionNameSet(String roleName);
}
