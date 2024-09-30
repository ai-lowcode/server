package cn.com.axel.sys.service.impl;

import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.dataset.datatable.MetaDataHeader;
import cn.com.axel.common.dataset.datatable.MetaDataTable;
import cn.com.axel.common.dataset.datatable.MetaHeaderDataTable;
import cn.com.axel.common.dataset.enums.TargetType;
import cn.com.axel.common.dblink.db.DBAdapter;
import cn.com.axel.common.dblink.db.DBDialect;
import cn.com.axel.common.dblink.entity.DataSourceOptions;
import cn.com.axel.common.dblink.page.BoundSql;
import cn.com.axel.common.dblink.page.AlPageHelper;
import cn.com.axel.common.dblink.query.QueryHandler;
import cn.com.axel.common.dblink.service.DbConnectService;
import cn.com.axel.common.dblink.service.TableService;
import cn.com.axel.sys.api.entity.DbConnect;
import cn.com.axel.sys.api.entity.FieldInfo;
import cn.com.axel.sys.api.entity.TableInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author: axel
 * @description: 表信息
 * @date: 2022/8/31 23:05
 */
@Service
@RefreshScope
public class TableServiceImpl implements TableService {
    @Resource
    DbConnectService dbConnectService;
    @Value("${DBConnect.password.privateKey}")
    private String privateKey;

    @Override
    public List<FieldInfo> getFieldList(String connectId, String tableName, ReqPage reqPage) {
        verifyTableName(tableName);
        return queryT(connectId, FieldInfo.class, (build, dbName) -> build.getColumns(dbName, tableName), reqPage);
    }

    @Override
    public TableInfo getTableInfo(String connectId, String tableName, ReqPage reqPage) {
        verifyTableName(tableName);
        List<TableInfo> list = getTableList(connectId, tableName, reqPage);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getFirst();
    }

    @Override
    public List<TableInfo> getTableList(String connectId, String tableName, ReqPage reqPage) {
        verifyTableName(tableName);
        return queryT(connectId, TableInfo.class, (build, dbName) -> build.getTableInfo(dbName, tableName), reqPage);
    }

    @Override
    public Result<MetaHeaderDataTable> getHeaderDataTable(String connectId, String tableName, ReqPage reqPage) {
        verifyTableName(tableName);
        MetaDataTable table = getDataTable(connectId, tableName, reqPage);
        return Result.ok(new MetaHeaderDataTable(table), "获取表数据成功");
    }

    @Override
    public MetaDataTable getDataTable(String connectId, String tableName, ReqPage reqPage) {
        verifyTableName(tableName);
        return query(connectId, "select * from " + tableName, reqPage);
    }

    private void verifyTableName(String tableName) {
        if (tableName == null) {
            return;
        }
        if (tableName.contains(" ")) {
            throw new MyRuntimeException("错误：表名不允许包含空格");
        }
        if (tableName.length() > 128) {
            throw new MyRuntimeException("错误：表名称太长");
        }
        if (!StringUtils.isMatch("^[a-zA-Z0-9_\\.\\-]+$", tableName)) {
            throw new MyRuntimeException("错误：表名不允许包含特殊字符");
        }
    }

    private DataSourceOptions<?> buildDBQuery(String connectId) {
        Result<DbConnect> result = dbConnectService.queryById(connectId);
        if (!result.isSuccess()) {
            throw new MyRuntimeException("错误:获取数据库连接出错");
        }
        DbConnect dbConnect = result.getData();
        return QueryHandler.buildDataSourceOptions(dbConnect, privateKey);
    }

    /**
     * 查询数据
     *
     * @param connectId 连接ID
     * @param sql       sql语句
     * @return 返回结果
     */
    private MetaDataTable query(String connectId, String sql, ReqPage reqPage) {
        DataSourceOptions<?> dataSourceOptions = buildDBQuery(connectId);
        if (reqPage != null) {
            AlPageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        }
        return QueryHandler.query(dataSourceOptions, sql);
    }

    private <T> List<T> queryT(String connectId, Class<T> cls, BiFunction<DBDialect, String, BoundSql> function, ReqPage reqPage) {
        DataSourceOptions<?> dataSourceOptions = buildDBQuery(connectId);
        DBDialect dialect = DBAdapter.getDBDialect(dataSourceOptions.getDbType());
        if (reqPage != null) {
            AlPageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        }
        return QueryHandler.queryT(dataSourceOptions, function.apply(dialect, dataSourceOptions.getDbName()), cls);
    }

    /**
     * 获取数据集列头
     *
     * @param connectId 连接ID
     * @param tableName 表名
     * @return 返回数据集列头
     */
    @Override
    public List<MetaDataHeader> getDataHeaders(String connectId, String tableName, ReqPage reqPage) {
        List<FieldInfo> list = getFieldList(connectId, tableName, reqPage);
        List<MetaDataHeader> headers = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return headers;
        }
        for (FieldInfo fieldInfo : list) {
            headers.add(new MetaDataHeader()
                    .setFieldName(fieldInfo.getFieldName())
                    .setColName(fieldInfo.getFieldName())
                    .setDataType(fieldInfo.getType())
                    .setTargetType(TargetType.ORIGINAL)
                    .setTableAlias(tableName)
                    .setComment(fieldInfo.getComment()));
        }
        return headers;
    }

}
