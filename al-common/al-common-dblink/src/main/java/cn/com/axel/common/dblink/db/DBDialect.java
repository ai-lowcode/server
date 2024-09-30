package cn.com.axel.common.dblink.db;

import cn.com.axel.common.dblink.page.BoundSql;

/**
 * @description: 代码构建相关语句
 * @author: axel
 * @date: 2023/3/23 22:08
 */
public interface DBDialect {
    String getJdbc(String host, String port, String dbName);

    /**
     * 获取表字段信息
     * <p>
     * 本方法旨在通过给定的数据库名称和表名称，获取对应表的字段信息
     * 用于动态SQL或查询构建前的准备，确保后续操作基于正确的表结构执行
     *
     * @param dbName    库名，指定要查询表字段信息的数据库
     * @param tableName 表名，指定要查询字段信息的具体表
     * @return BoundSql 包含指定表字段信息的绑定SQL对象
     */
    BoundSql getColumns(String dbName, String tableName);

    /**
     * 获取表信息
     *
     * @param dbName    库名
     * @param tableName 表名
     * @return BoundSql 对象，包含表的信息
     */
    BoundSql getTableInfo(String dbName, String tableName);
}
