package com.fasteam.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created by hp on 2017/9/14.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600 * 6)  // 使用redis session后，原boot的server.session.timeout属性无效
public class SessionConfig {

    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("NI_JSESSIONID");
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }
}
