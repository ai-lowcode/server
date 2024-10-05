package cn.com.axel.sys.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 字典
 * @Author: axel
 * @date: 2023-01-03
 * @Version: V0.0.1
 */
public interface DictService extends IService<Dict> {
    Result<Dict> updateDict(Dict dict);

    /**
     * 字典编码是否存在
     *
     * @param id id
     * @param dictCode 字典编码
     * @return 返回是否存在
     */
    boolean isDictCodeExist(String id, String dictCode);

    /**
     * 删除字典
     *
     * @param id id
     * @return 返回删除结果
     */
    Result<Boolean> deleteDict(String id);
}
