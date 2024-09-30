package cn.com.axel.common.api;

import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.dataset.datatable.MetaDataHeader;
import cn.com.axel.common.dataset.datatable.MetaHeaderDataTable;
import cn.com.axel.common.dblink.service.DbConnectService;
import cn.com.axel.common.dblink.service.TableService;
import cn.com.axel.sys.api.entity.DbConnect;
import cn.com.axel.sys.api.entity.FieldInfo;
import cn.com.axel.sys.api.entity.TableInfo;
import cn.com.axel.sys.api.remote.RemoteDbConnectService;
import cn.com.axel.sys.api.req.ReqDbConnect;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @description: 数据库连接服务单体实现类
 * @author: axel
 * @date: 2024/4/16
 */
@Service("remoteDbConnectService")
public class BootDbConnectService implements RemoteDbConnectService {
    @Resource
    TableService tableService;
    @Resource
    DbConnectService dbConnectService;

    @Override
    public Result<PageResult<DbConnect>> queryPageList(String origin, ReqDbConnect reqDbConnect, ReqPage reqPage) {
        return dbConnectService.queryPageList(reqDbConnect, reqPage);
    }

    @Override
    public Result<DbConnect> queryById(String origin, String id) {
        return dbConnectService.queryById(id);
    }

    @Override
    public Result<PageResult<TableInfo>> getTableList(String origin, String connectId, String tableName, ReqPage reqPage) {
        return Result.ok(new PageResult<>(tableService.getTableList(connectId, tableName, reqPage)), "获取表列表成功");
    }

    @Override
    public Result<PageResult<FieldInfo>> getFieldList(String origin, String connectId, String tableName, ReqPage reqPage) {
        return Result.ok(new PageResult<>(tableService.getFieldList(connectId, tableName, reqPage)), "获取字段列表成功");
    }

    @Override
    public Result<MetaHeaderDataTable> getDataTable(String origin, String connectId, String tableName, ReqPage reqPage) {
        return tableService.getHeaderDataTable(connectId, tableName, reqPage);
    }

    @Override
    public Result<List<MetaDataHeader>> getDataHeaders(String origin, String connectId, String tableName, ReqPage reqPage) {
        return Result.ok(tableService.getDataHeaders(connectId, tableName, reqPage), "获取表列头成功");
    }
}
