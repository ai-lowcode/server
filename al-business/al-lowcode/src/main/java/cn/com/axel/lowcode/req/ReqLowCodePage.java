package cn.com.axel.lowcode.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 低代码页面
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Data
@Accessors(chain = true)
@Schema(description = "低代码页面请求参数")
public class ReqLowCodePage {
    @Schema(description = "页面名称")
    private String pageName;
    @Schema(description = "页面状态")
    private Short pageStatus;
    @Schema(description = "页面标识")
    private Short pageSlug;
}
