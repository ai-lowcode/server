package cn.com.axel.sys.service.impl;

import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.entity.SysConfig;
import cn.com.axel.sys.mapper.SysConfigMapper;
import cn.com.axel.sys.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 界面配置
 * @author: axel
 * @date: 2023-03-07
 * @version: V1.3.1
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public List<SysConfig> querySysConfig(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new MyRuntimeException("错误：用户ID或类型不允许为空");
        }
        return baseMapper.selectList(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getUserId, userId));
    }

    @Override
    public SysConfig querySysConfig(String userId, Integer type) {
        if (StringUtils.isEmpty(userId) || null == type) {
            throw new MyRuntimeException("错误：用户ID或类型不允许为空");
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getUserId, userId)
                .eq(SysConfig::getType, type));
    }

    @Override
    public Result<SysConfig> saveSysConfig(SysConfig sysConfig) {
        String userId = AuthInfoUtils.getCurrentUserId();
        SysConfig config = querySysConfig(userId, sysConfig.getType());
        if (config != null) {
            config.setConfig(sysConfig.getConfig());
            if (baseMapper.updateById(config) == 1) {
                return Result.ok(sysConfig, "界面配置-编辑成功!");
            }
            return Result.fail(sysConfig, "错误:界面配置-编辑失败!");
        }
        sysConfig.setUserId(userId);
        if (baseMapper.insert(sysConfig) == 1) {
            return Result.ok(sysConfig, "界面配置-新增成功!");
        }
        return Result.fail(sysConfig, "错误:界面配置-新增失败!");
    }
}
