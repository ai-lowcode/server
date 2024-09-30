package cn.com.axel.oauth;

import cn.com.axel.common.cloud.annotation.AutoCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author: axel
 * @description: 统一认证中心
 * @date: 2021/11/15 15:05
 */
@Slf4j
@AutoCloud
public class AlOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlOauthApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel认证中心启动成功-----------------------
                \t""");
    }
}
