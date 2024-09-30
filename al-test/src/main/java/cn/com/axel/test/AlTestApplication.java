package cn.com.axel.test;

import cn.com.axel.common.app.annotation.AutoApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author: axel
 * @description: Axel测试中心启动
 * @date: 2021/12/3 17:22
 */
@Slf4j
@AutoApp
public class AlTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlTestApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel测试中心启动成功-----------------------
                \t""");
    }
}
