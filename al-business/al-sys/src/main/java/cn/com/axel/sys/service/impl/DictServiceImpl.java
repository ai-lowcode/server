package cn.com.axel.sys.service.impl;

import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.sys.req.ReqDictItem;
import cn.com.axel.common.sys.service.DictItemService;
import cn.com.axel.sys.api.entity.DictItem;
import cn.com.axel.sys.cache.DictCache;
import cn.com.axel.sys.entity.Dict;
import cn.com.axel.sys.mapper.DictMapper;
import cn.com.axel.sys.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @Description: 字典
 * @Author: axel
 * @date: 2023-01-03
 * @Version: V1.3.1
 */
@Service
@SuppressWarnings({"rawtypes"})
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    DictItemService dictItemService;
    @Resource
    DictCache dictCache;

    @Override
    @Transactional
    public Result<Dict> updateDict(Dict dict) {
        List<DictItem> list = dictItemService.getDictItems(new ReqDictItem().setDictId(dict.getId()));
        String orgCode = null;
        if (!list.isEmpty()) {
            orgCode = list.getFirst().getDictCode();
        }
        if (orgCode != null && !orgCode.equals(dict.getDictCode())) {
            for (DictItem<?> item : list) {
                item.setDictCode(dict.getDictCode());
            }
            if (!dictItemService.updateBatchById(list)) {
                throw new MyRuntimeException("错误:修改字典项失败!");
            }
        }
        if (baseMapper.updateById(dict) > 0) {
            dictCache.removeOneCache(orgCode);
            return Result.ok(dict, "字典-编辑成功!");
        }
        throw new MyRuntimeException("错误:编辑字典失败!");
    }

    @Override
    public boolean isDictCodeExist(String id, String dictCode) {
        return baseMapper.isDictCodeExist(id, dictCode) > 0;
    }

    @Override
    @Transactional
    public Result<Boolean> deleteDict(String id) {
        Dict dict = baseMapper.selectById(id);
        if (dict != null && baseMapper.deleteById(id) == 1) {
            dictItemService.deleteDictItemsByCode(dict.getDictCode());
            dictCache.removeOneCache(dict.getDictCode());
            return Result.ok(true, "字典-删除成功!");
        }
        return Result.fail(false, "错误:字典-删除失败!");
    }
}