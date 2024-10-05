package cn.com.axel.demo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: demo_data_scope
 * @author: axel
 * @date: 2024-09-04
 * @version: V0.0.1
 */
@Data
@Accessors(chain = true)
@Schema(description = "demo_data_scope请求参数")
public class ReqDemoDataScope {
}
