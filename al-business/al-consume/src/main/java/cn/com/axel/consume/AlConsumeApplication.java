package cn.com.axel.consume;

import cn.com.axel.common.app.annotation.AutoApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: 消费端
 * @author: axel
 * @date: 2023/3/1 16:25
 */
@Slf4j
@AutoApp
public class AlConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlConsumeApplication.class, args);
        log.info("""
                
                \t----------------------------------------------------------
                \t\
                
                \t--------------------Axel调度中心消费端测试样例启动成功-----------------------
                \t""");
    }
}
