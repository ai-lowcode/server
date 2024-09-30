package cn.com.axel.sys.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 界面配置
 * @author: axel
 * @date: 2023-03-07
 * @version: V1.3.1
 */
public interface SysConfigService extends IService<SysConfig> {
    List<SysConfig> querySysConfig(String userId);

    SysConfig querySysConfig(String userId, Integer type);

    Result<SysConfig> saveSysConfig(SysConfig sysConfig);
}
