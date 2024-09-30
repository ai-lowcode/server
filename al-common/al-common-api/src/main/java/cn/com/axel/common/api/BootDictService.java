package cn.com.axel.common.api;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.sys.service.DictItemService;
import cn.com.axel.sys.api.entity.DictItem;
import cn.com.axel.sys.api.remote.RemoteDictService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @description: 远程字典服务单实例实现
 * @author: axel
 * @date: 2024/4/16
 */
@SuppressWarnings("rawtypes")
@Service("remoteDictService")
public class BootDictService implements RemoteDictService {

    @Resource
    DictItemService dictItemService;

    @Override
    public Result<List<DictItem>> queryByCode(String origin, String dictCode) {
        return dictItemService.queryByCode(dictCode);
    }
}
