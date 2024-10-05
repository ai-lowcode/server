package cn.com.axel.sys.req;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 字典
 * @Author: axel
 * @date: 2023-01-03
 * @Version: V0.0.1
 */
@Data
@Accessors(chain = true)
@Schema(description = "字典请求参数")
public class ReqDict {
    @Schema(description = "字典名称")
    private String dictName;
    @Schema(description = "字典编码")
    private String dictCode;
    @Schema(description = "状态(0正常 1停用)")
    private Integer status;
}
