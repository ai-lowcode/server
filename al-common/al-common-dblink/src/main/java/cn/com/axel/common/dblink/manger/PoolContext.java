package cn.com.axel.common.dblink.manger;

import cn.com.axel.common.dblink.dbpool.PoolWrapper;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 连接池上下文
 * @author: axel
 * @date: 2023/3/16 11:00
 */
public class PoolContext {
    //连接池有效期(一天 单位:秒)
    @Setter
    @Getter
    private long expire = 24 * 60 * 60;
    //检查时间
    // 当datasource被获取的1分钟内，不执行清理线程，降低线程额外开销
    @Getter
    @Setter
    private long checkTime;
    //连接池包装类
    private final PoolWrapper<?> poolWrapper;

    public PoolContext(PoolWrapper<?> poolWrapper) {
        this.poolWrapper = poolWrapper;
    }

    public void close() {
        poolWrapper.close();
    }

    public DataSource getDataSource() {
        return poolWrapper.getDataSource();
    }

    public Connection getConnection() throws SQLException {
        return poolWrapper.getConnection();
    }

    Connection getConnection(long maxWaitMillis) throws SQLException {
        return poolWrapper.getConnection(maxWaitMillis);
    }

    public boolean isClose() {
        return poolWrapper.isClosed();
    }
}
