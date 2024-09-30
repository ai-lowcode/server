package cn.com.axel.scheduler.config;

import cn.com.axel.scheduler.config.properties.SchedulerProperties;
import cn.com.axel.scheduler.listener.AlJobListener;
import cn.com.axel.scheduler.listener.AlSchedulerListener;
import cn.com.axel.scheduler.listener.AlTriggerListener;
import org.quartz.JobListener;
import org.quartz.SchedulerListener;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description: 调度配置
 * @author: axel
 * @date: 2023/2/7 11:31
 */
@Configuration
public class SchedulerConfig {
    private final SchedulerProperties schedulerProperties;

    @Autowired
    public SchedulerConfig(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
    }

    @Bean
    public Properties quartzProperties() {
        // quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", schedulerProperties.getScheduler().getInstanceName());
        prop.put("org.quartz.scheduler.instanceId", schedulerProperties.getScheduler().getInstanceId());
        // 线程池配置
        prop.put("org.quartz.threadPool.class", schedulerProperties.getThreadPool().getPoolClass());
        prop.put("org.quartz.threadPool.threadCount", schedulerProperties.getThreadPool().getThreadCount());
        prop.put("org.quartz.threadPool.threadPriority", schedulerProperties.getThreadPool().getThreadPriority());
        // JobStore配置
        prop.put("org.quartz.jobStore.class", schedulerProperties.getJobStore().getJdbcClass());
        prop.put("org.quartz.jobStore.driverDelegateClass", schedulerProperties.getJobStore().getDriverDelegateClass());
        prop.put("org.quartz.jobStore.useProperties", schedulerProperties.getJobStore().getUseProperties());
        prop.put("org.quartz.jobStore.isClustered", schedulerProperties.getJobStore().getIsClustered());
        prop.put("org.quartz.jobStore.tablePrefix", schedulerProperties.getJobStore().getTablePrefix());
        prop.put("org.quartz.jobStore.dataSource", schedulerProperties.getJobStore().getDataSource());
        prop.put("org.quartz.jobStore.clusterCheckinInterval", schedulerProperties.getJobStore().getClusterCheckinInterval());
        prop.put("org.quartz.jobStore.misfireThreshold", schedulerProperties.getJobStore().getMisfireThreshold());
        //设置数据库连接类
        prop.put("org.quartz.dataSource.al_scheduler.connectionProvider.class", "cn.com.axel.scheduler.common.AlConnectionProvider");

        return prop;
    }

    @Bean("alSchedulerFactoryBean")
    @Primary
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties());
        factory.setJobFactory(new SpringBeanJobFactory());
        // 延时启动
        factory.setStartupDelay(schedulerProperties.getStartupDelay());
        // 设置自动启动，默认为true
        factory.setAutoStartup(schedulerProperties.getAutoStartup());
        factory.setApplicationContextSchedulerContextKey(schedulerProperties.getApplicationContextSchedulerContextKey());
        factory.setOverwriteExistingJobs(schedulerProperties.getOverwriteExistingJobs());
        //添加监听器
        factory.setSchedulerListeners(schedulerListener());
        factory.setGlobalJobListeners(jobListener());
        factory.setGlobalTriggerListeners(triggerListener());
        return factory;
    }

    @Bean(name = "alSchedulerListener")
    public SchedulerListener schedulerListener() {
        return new AlSchedulerListener();
    }

    @Bean(name = "alJobListener")
    public JobListener jobListener() {
        return new AlJobListener();
    }

    @Bean(name = "alTriggerListener")
    public TriggerListener triggerListener() {
        return new AlTriggerListener();
    }

}
