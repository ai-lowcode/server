package cn.com.axel.common.code.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description: 搜索请求
 * @author: axel
 * @date: 2023/5/9 19:31
 */
@Schema(description = "查询条件")
@Data
@Accessors(chain = true)
public class ReqSearch {
    @Schema(description = "字段")
    private String field;
    @Schema(description = "查询条件")
    private String condition;
    @Schema(description = "组件 索引0组件名称1字典编码")
    private List<String> component;
}
