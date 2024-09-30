package cn.com.axel.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: axel
 * @date: 2021/8/11 11:44
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class AlGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(cn.com.axel.gateway.AlGatewayApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel网关服务启动成功-----------------------
                \t""");
    }
}
