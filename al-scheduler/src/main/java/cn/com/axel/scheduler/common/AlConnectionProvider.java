package cn.com.axel.scheduler.common;

import cn.com.axel.common.core.utils.SpringBeanFactory;
import org.quartz.utils.ConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 定义数据库provider
 * @author: axel
 * @date: 2023/2/7 22:28
 */
public class AlConnectionProvider implements ConnectionProvider {
    DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void initialize() {
        dataSource = SpringBeanFactory.getBean("dataSource");
    }
}
