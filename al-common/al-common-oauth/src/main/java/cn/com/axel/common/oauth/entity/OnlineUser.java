package cn.com.axel.common.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 在线用户
 * @author: axel
 * @date: 2023/3/8 18:20
 */
@Schema(description = "在线用户")
@Data
@Accessors(chain = true)
public class OnlineUser {
    @Schema(description = "帐号")
    private String account;
    @Schema(description = "客户端ID")
    private String clientId;
    @Schema(description = "sessionId信息")
    private String sid;
    @Schema(description = "登录IP")
    private String ip;
    @Schema(description = "登录模式 0 浏览器 1微信")
    private int loginMode;
    @Schema(description = "登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    @Schema(description = "过期时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expire;
}
