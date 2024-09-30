package cn.com.axel.common.dataset.datatable;

import cn.com.axel.common.core.web.PageResult;
import lombok.Data;

/**
 * @description: 带头信息元数据表
 * @author: axel
 * @date: 2023/4/5 0:01
 */
@Data
public class MetaHeaderDataTable {
    /**
     * 表格
     */
    private PageResult<MetaDataRow> table;
    /**
     * 头信息
     */
    private MetaDataHeaders headers;

    public MetaHeaderDataTable(MetaDataTable metaDataTable) {
        this.table = new PageResult<>(metaDataTable);
        this.headers = metaDataTable.getColHeaders();
    }
}
