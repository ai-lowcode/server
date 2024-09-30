package cn.com.axel.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: Axel监控中心启动类
 * @author: axel
 * @date: 2023/1/26 23:11
 */
@SpringBootApplication
@EnableAdminServer
@Slf4j
public class AlMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlMonitorApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel监控中心启动成功-----------------------
                \t""");
    }
}
