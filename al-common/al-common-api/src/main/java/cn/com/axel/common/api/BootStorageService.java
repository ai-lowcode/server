package cn.com.axel.common.api;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.file.service.StorageService;
import cn.com.axel.common.storage.api.entity.StorageInfo;
import org.springframework.stereotype.Service;
import cn.com.axel.common.storage.api.remote.RemoteStorageService;

import jakarta.annotation.Resource;

/**
 * @description: 缓存接口单实例实现
 * @author: axel
 * @date: 2024/4/16
 */
@Service("remoteStorageService")
public class BootStorageService implements RemoteStorageService {
    @Resource
    StorageService storageService;

    @Override
    public Result<StorageInfo> queryByKey(String origin, String fileKey) {
        return storageService.queryByKey(fileKey);
    }

    @Override
    public Result<Boolean> logicDelete(String origin, String id) {
        return storageService.logicDelete(id);
    }
}
