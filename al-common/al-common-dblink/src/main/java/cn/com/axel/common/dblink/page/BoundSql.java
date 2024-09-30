package cn.com.axel.common.dblink.page;

import cn.com.axel.common.dblink.entity.QueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: sql包装类
 * @author: axel
 * @date: 2023/3/21 23:01
 */
@Data
@Accessors(chain = true)
@Schema(description = "sql包装")
public class BoundSql {
    @Schema(description = "查询SQL")
    private String sql;
    @Schema(description = "查询参数")
    private List<QueryParam> params;

    public BoundSql() {
        this("");
    }

    /**
     * SQL包装类构造函数
     *
     * @param sql sql语句
     */
    public BoundSql(String sql) {
        this(sql, null);
    }

    /**
     * SQL包装类构造函数
     *
     * @param sql    sql语句
     * @param params 参数
     */
    public BoundSql(String sql, List<QueryParam> params) {
        this.sql = sql;
        if (params == null) {
            params = new ArrayList<>();
        }
        this.params = params;
    }
}
