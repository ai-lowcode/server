package cn.com.axel.common.oauth.common;

import cn.com.axel.common.core.enums.DeviceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 登录互斥属性
 * @author: axel
 * @date: 2023/5/7 1:14
 */
@Data
@Accessors(chain = true)
public class LoginMutexEntity {
    @Schema(description = "设备类型")
    private DeviceType deviceType;
    @Schema(description = "设备ID web端为sessionid app端为认证app的mac地址 微信为openid")
    private String deviceId;
    @Schema(description = "用户id")
    private String userId;
}
