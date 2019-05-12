package com.fasteam.app.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行加载配置文件信息
 * Created by Administrator on 2017/10/8.
 */
@Component
public class AppProperties implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AppProperties.class);

    //    @Value("${logo.dir}")
    public String logoDir;


    @Value("${static.baseUrl}")
    public String staticBaseUrl;

    @Value("${system.baseUrl}")
    public String systemBaseUrl;

    @Override
    public void run(String... strings) {
        LOG.info(">>>>>>>>>>>>>>>>>>>服务启动执行，加载配置文件信息begin<<<<<<<<<<<<<<<<<<<<<");
        LOG.info(">>>>>>>>>>>>>>>>>>>服务启动执行，加载配置文件信息end<<<<<<<<<<<<<<<<<<<<<");
    }
}
