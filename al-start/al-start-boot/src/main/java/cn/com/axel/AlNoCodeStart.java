package cn.com.axel;

import cn.com.axel.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: Axel低代码单应用启动类
 * @author: axel
 * @date: 2024/1/26
 */
@Slf4j
@AutoWeb
public class AlNoCodeStart {
    public static void main(String[] args) {
        SpringApplication.run(AlNoCodeStart.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel低代码平台启动成功-----------------------
                \t""");
    }
}
