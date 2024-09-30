package cn.com.axel.common.dblink.dbpool;

import cn.com.axel.common.dblink.entity.DataSourceOptions;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 连接池包装类
 * @author: axel
 * @date: 2023/3/16
 */
public interface PoolWrapper<T> {
    PoolWrapper<T> wrap(DataSourceOptions<T> linkOption);

    void close();

    DataSource getDataSource();

    Connection getConnection() throws SQLException;

    Connection getConnection(long maxWaitMillis) throws SQLException;

    boolean isClosed();
}
