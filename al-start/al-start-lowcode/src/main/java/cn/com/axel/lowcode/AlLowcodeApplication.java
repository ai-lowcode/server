package cn.com.axel.lowcode;

import cn.com.axel.common.cloud.annotation.AutoCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author: axel
 * @description: 其他web业务服务参考类
 * @date: 2022/12/16 10:01
 */
@Slf4j
@AutoCloud
public class AlLowcodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlLowcodeApplication.class, args);
        log.info("""
                
                \t------------------------------------------------------------
                \t
                \t--------------------Axel样例服务启动成功-----------------------
                \t""");
    }
}
