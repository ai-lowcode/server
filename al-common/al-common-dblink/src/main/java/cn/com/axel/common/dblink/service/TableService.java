package cn.com.axel.common.dblink.service;

import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.dataset.datatable.MetaDataHeader;
import cn.com.axel.common.dataset.datatable.MetaDataTable;
import cn.com.axel.common.dataset.datatable.MetaHeaderDataTable;
import cn.com.axel.sys.api.entity.FieldInfo;
import cn.com.axel.sys.api.entity.TableInfo;

import java.util.List;

/**
 * @author: axel
 * @description: 表信息
 * @date: 2022/8/31 22:59
 */
public interface TableService {
    /**
     * 获取表字段信息
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return 返回字段列表
     */
    List<FieldInfo> getFieldList(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取表信息
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return 返回表信息
     */
    TableInfo getTableInfo(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取表列表
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return 返回表列表
     */
    List<TableInfo> getTableList(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取带头信息数据
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @param reqPage 翻页参数
     * @return 返回头信息数据
     */
    Result<MetaHeaderDataTable> getHeaderDataTable(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取数据
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return 返回数据
     */
    MetaDataTable getDataTable(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取字段
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @param reqPage   分页
     * @return 返回字段
     */
    List<MetaDataHeader> getDataHeaders(String connectId, String tableName, ReqPage reqPage);
}
