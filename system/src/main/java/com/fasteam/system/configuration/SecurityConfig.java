package com.fasteam.system.configuration;

import com.fasteam.system.security.AppAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/9/10.
 */
@Configuration
@EnableWebSecurity  //开启spring security 功能
@EnableGlobalMethodSecurity(prePostEnabled = true)  //允许进入页面方法前校验
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    //不做权限控制的url
    private static final String[] PERMIT_URLS = new String[]{
            "/css/**", "/fonts/**", "/js/**", "/images/**", "/favicon.ico", "/druid/**",
            "/auth/**", "/sysLog/handler",
            "/dictionary/type/", "/attachment/partition", "/sysUser/loginUser",
            "/sysResource/getSecondMenu", "/attachment/list/fatherId/**", "/attachment/update/status"
    };

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable(); //处理 Refused to display 'http://loc... in a frame because it set 'X-Frame-Options' to 'DENY'.
        //自定义登录页面,关闭csrf放在循环定向
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(PERMIT_URLS).permitAll()
                .antMatchers("/**").hasAnyAuthority("admin,superAdmin,user")
                .anyRequest().authenticated()  //表示任何请求都要权限认证
                .and()
                .formLogin()
                .loginPage("/auth/login")    //需要用户登录时候，转到的登录页面
                .failureUrl("/auth/login?error")
                .defaultSuccessUrl("/index/hello")    //登录成功后跳转页面
                .permitAll()    //表示请求任何人都可以访问
                .and()
                .logout()       //默认注销行为logout, 默认跳转到登录页面
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(appAuthenticationProvider()).userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public LoggerListener loggerListener() {
        LOG.info("org.springframework.security.authentication.event.LoggerListener");
        return new LoggerListener();
    }

    @Bean
    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
        LOG.info("org.springframework.security.access.event.LoggerListener");
        return new org.springframework.security.access.event.LoggerListener();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AppAuthenticationProvider appAuthenticationProvider() {
        return new AppAuthenticationProvider();
    }

    @Bean
    public HttpFirewall httpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
//        firewall.setAllowBackSlash(true);
        return firewall;
    }

}
