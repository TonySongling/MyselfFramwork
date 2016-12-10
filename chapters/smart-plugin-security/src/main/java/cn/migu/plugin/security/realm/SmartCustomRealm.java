package cn.migu.plugin.security.realm;

import cn.migu.plugin.security.SecurityConstant;
import cn.migu.plugin.security.SmartSecurity;
import cn.migu.plugin.security.password.Md5CredentialsMatcher;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于Smart的自定义Realm(需要实现SmartSecurity接口)
 * Created by Song on 2016/12/11.
 */
public class SmartCustomRealm extends AuthorizingRealm{

    private final SmartSecurity smartSecurity;

    public SmartCustomRealm(SmartSecurity smartSecurity) {
        this.smartSecurity = smartSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        //使用MD5加密
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){

        if (principalCollection == null){
            throw new AuthorizationException("paramerter principals is null");
        }
        //获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principalCollection);

        //通过SmartSecurity接口并根据用户名获取角色集合
        Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);

        //通过SmartSecurity接口并根据角色名获取与其对应的权限集合
        Set<String> permissionNameSet = new HashSet<String>();
        if (roleNameSet != null && roleNameSet.size() > 0){
            for (String roleName : roleNameSet){
                Set<String> currentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }

        //将角色名集合与权限名集合放入AuthorizationInfo对象中，便于后续的授权使用
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionNameSet);
        return authorizationInfo;
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken == null){
            throw new AuthenticationException("parameter token is null");
        }
        //通过AuthenticationToken对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken)authenticationToken).getUsername();

        //通过SmartSecurity接口并根据用户获取数据库中存放的密码
        String password = smartSecurity.getPassowrd(username);

        //将同户名与密码放入AuthenticationInfo对象中，便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        return authenticationInfo;
    }
}
