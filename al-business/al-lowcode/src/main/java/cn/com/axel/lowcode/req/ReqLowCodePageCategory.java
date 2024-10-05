package cn.com.axel.lowcode.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 低代码页面分类
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Data
@Accessors(chain = true)
@Schema(description = "低代码页面分类请求参数")
public class ReqLowCodePageCategory {
    @Schema(description = "分类名称")
    private String name;
    @Schema(description = "分类状态")
    private Short status;
    @Schema(description = "分类标识")
    private Short slug;
}
