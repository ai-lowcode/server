package cn.com.axel.storage.service.impl;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.storage.api.entity.StorageInfo;
import cn.com.axel.storage.mapper.StorageMapper;
import cn.com.axel.common.file.service.StorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: 文件缓存
 * @author: axel
 * @date: 2023-01-05
 * @version: V0.0.1
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, StorageInfo> implements StorageService {

    @Override
    public Result<StorageInfo> queryByKey(String fileKey) {
        StorageInfo storageInfo = baseMapper.selectOne(new LambdaQueryWrapper<StorageInfo>().eq(StorageInfo::getFileKey, fileKey));
        if (null == storageInfo) {
            return Result.fail("错误:获取文件信息失败!");
        }
        return Result.ok(storageInfo, "获取文件信息-查询成功!");
    }

    @Override
    public Result<Boolean> logicDelete(String id) {
        if (baseMapper.updateById(new StorageInfo().setId(id).setDelFlag(1)) > 0) {
            return Result.ok(true, "文件存储-逻辑删除成功!");
        }
        return Result.fail(false, "错误:文件存储-逻辑删除失败!");
    }
}
