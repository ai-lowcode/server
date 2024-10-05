package cn.com.axel.lowcode.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.lowcode.entity.LowCodePageCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 低代码页面分类
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
public interface LowCodePageCategoryService extends IService<LowCodePageCategory> {
    Result<LowCodePageCategory> addPageCategory(LowCodePageCategory lowCodePageCategory);
    Result<LowCodePageCategory> editPageCategory(LowCodePageCategory lowCodePageCategory);
    Result<Boolean> deletePageCategory(String id);
    Result<Boolean> deleteBatchPageCategory(String ids);
}
