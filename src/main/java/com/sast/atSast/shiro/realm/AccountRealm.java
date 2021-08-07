package com.sast.atSast.shiro.realm;

import com.sast.atSast.model.Account;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.RedisService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author: 風楪fy
 * @create: 2021-07-16 03:05
 **/
public class AccountRealm extends AuthorizingRealm {

    //因为shiroConfig中已经将AccountRealm加入，所以可以直接注入
    @Autowired
    private AccountService accountService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        List<String> roles = accountService.findRolesByEmail(primaryPrincipal);
        if(!CollectionUtils.isEmpty(roles)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(simpleAuthorizationInfo::addRole);
            return simpleAuthorizationInfo;
        }
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();//得到账号（邮箱）
        Account account = accountService.findAccountByEmail(principal);
        if(!ObjectUtils.isEmpty(account)){
            return new SimpleAuthenticationInfo(account.getEmail(),
                    account.getPassword(),
                    ByteSource.Util.bytes(account.getSalt()),
                    this.getName());
        }
        return null;
    }
}
