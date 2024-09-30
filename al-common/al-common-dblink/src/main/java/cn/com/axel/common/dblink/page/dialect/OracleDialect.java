package cn.com.axel.common.dblink.page.dialect;

import cn.com.axel.common.core.constants.DataConstant;
import cn.com.axel.common.core.enums.DataType;
import cn.com.axel.common.dblink.entity.QueryParam;
import cn.com.axel.common.dblink.page.BoundSql;
import cn.com.axel.common.dblink.query.BaseQuery;
import com.github.pagehelper.Page;

/**
 * @description: oracle相关方言
 * @author: axel
 * @date: 2023/3/21 23:04
 */
public class OracleDialect extends AbstractDialect {
    public OracleDialect(BaseQuery baseQuery) {
        super(baseQuery);
    }

    @Override
    protected BoundSql getPageSql(BoundSql boundSql, Page<?> page) {
        String sql = "SELECT * FROM ( " +
                " SELECT TMP_PAGE.*, ROWNUM " + DataConstant.ORACLE_ROW + " FROM (" +
                boundSql.getSql() +
                ") TMP_PAGE)" +
                " WHERE " + DataConstant.ORACLE_ROW + " <= ? AND " + DataConstant.ORACLE_ROW + " > ?";
        boundSql.getParams().add(new QueryParam().setValue(page.getEndRow()).setType(DataType.INTEGER));
        boundSql.getParams().add(new QueryParam().setValue(page.getStartRow()).setType(DataType.INTEGER));
        return boundSql.setSql(sql);
    }
}