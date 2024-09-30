package cn.com.axel.demo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 销售订单
 * @author: axel
 * @date: 2024-09-13
 * @version: V1.3.1
 */
@Data
@Accessors(chain = true)
@Schema(description = "销售订单请求参数")
public class ReqDemoOrder {
    @Schema(description = "姓名")
    private String userName;
    @Schema(description = "订单状态")
    private Short orderStatus;
    @Schema(description = "支付类型")
    private Short payType;
    @Schema(description = "配送方式")
    private Short deliveryType;
}