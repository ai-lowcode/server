package cn.com.axel.oauth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.com.axel.oauth.config.properties.WeChatProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * @author: axel
 * @description: 微信配置
 * @date: 2021/12/14 9:20
 */
@Configuration
public class WeChatConfig {
    @Resource
    private WeChatProperties weChatProperties;

    @Bean
    public WxMaService wxMaService() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(weChatProperties.getAppId());
        config.setSecret(weChatProperties.getSecret());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}