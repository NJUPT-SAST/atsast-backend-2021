package com.sast.atSast.config;

import com.sast.atSast.filter.ShiroFormAuthenticationFilter;
import com.sast.atSast.filter.ShiroRolesAuthorizationFilter;
import com.sast.atSast.shiro.realm.AccountRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 風楪fy
 * @create: 2021-07-16 02:56
 **/
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> map = new HashMap<>();
//        map.put("/user/login","anon");//anon表示公共资源
//        map.put("/user/register","anon");//anon表示公共资源
//        map.put("/**","authc");//authc表示请求这个资源需要认证和授权
        //设置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //使用自定义的过滤器
        Map<String, Filter> customizeMap = new HashMap<>();
        customizeMap.put("authc", new ShiroFormAuthenticationFilter());
        customizeMap.put("roles", new ShiroRolesAuthorizationFilter());// role 表示必须具有某个角色权限才能访问
        // 添加自定义的过滤器
        shiroFilterFactoryBean.setFilters(customizeMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean
    public Realm getRealm() {
        AccountRealm accountRealm = new AccountRealm();

        //修改凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("Md5");
        credentialsMatcher.setHashIterations(1024);
        accountRealm.setCredentialsMatcher(credentialsMatcher);

//        开启缓存管理(ehcache)
        accountRealm.setCacheManager(new EhCacheManager());
        accountRealm.setCachingEnabled(true);//开启全局缓存
        accountRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        accountRealm.setAuthenticationCacheName("authenticationCache");//重命名（可以不加）
        accountRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        accountRealm.setAuthorizationCacheName("authorizationCache");//重命名（可以不加）


        return accountRealm;
    }

}
