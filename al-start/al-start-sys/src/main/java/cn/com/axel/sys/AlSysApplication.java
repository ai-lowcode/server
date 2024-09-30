package cn.com.axel.sys;

import cn.com.axel.common.cloud.annotation.AutoCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author: axel
 * @description: 系统业务中心启动类
 * @date: 2022/9/2
 */

@Slf4j
@AutoCloud
public class AlSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlSysApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel系统业务中心启动成功-----------------------
                \t""");
    }
}
