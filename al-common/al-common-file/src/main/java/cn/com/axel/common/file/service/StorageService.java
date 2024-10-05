package cn.com.axel.common.file.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.storage.api.entity.StorageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 文件缓存
 * @author: axel
 * @date: 2023-01-05
 * @version: V0.0.1
 */
public interface StorageService extends IService<StorageInfo> {

    Result<StorageInfo> queryByKey(String fileKey);

    Result<Boolean> logicDelete(String id);
}
