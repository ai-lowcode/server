package cn.com.axel.storage;

import cn.com.axel.common.cloud.annotation.AutoCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: Axel文件中心启动类
 * @author: axel
 * @date: 2023/1/5 16:34
 */
@Slf4j
@AutoCloud
public class AlStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlStorageApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel文件中心启动成功-----------------------
                \t""");
    }
}
