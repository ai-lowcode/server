package cn.com.axel.scheduler;

import cn.com.axel.common.cloud.annotation.AutoCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 调度中心启动类
 * @author: axel
 * @date: 2023/2/3 15:15
 */
@AutoCloud
@Slf4j
@EnableScheduling
public class AlSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlSchedulerApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel调度中心启动成功-----------------------
                \t""");
    }
}
