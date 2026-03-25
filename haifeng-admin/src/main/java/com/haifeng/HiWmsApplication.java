package com.haifeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author haifeng
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HiWmsApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HiWmsApplication.class, args);
        System.out.println("------ Hi-WMS仓库管理系统启动成功 ------");
        //test
    }
}
