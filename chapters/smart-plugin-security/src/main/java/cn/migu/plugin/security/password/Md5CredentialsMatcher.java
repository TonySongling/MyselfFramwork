package cn.migu.plugin.security.password;

import cn.migu.framwork.util.CodecUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * Created by Song on 2016/12/10.
 */
public class Md5CredentialsMatcher implements CredentialsMatcher {
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        //获取从表单提交过来的密码、明文、尚未通过MD5加密
        String submitted = String.valueOf(((UsernamePasswordToken)authenticationToken).getPassword());
        //获取数据库中存储的密码，已通过MD5加密
        String encrypted = String.valueOf(authenticationInfo.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
