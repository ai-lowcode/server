package cn.com.axel.lowcode.service.impl;

import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.lowcode.entity.LowCodePageCategory;
import cn.com.axel.lowcode.mapper.LowCodePageCategoryMapper;
import cn.com.axel.lowcode.service.LowCodePageCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 低代码页面分类
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Service
public class LowCodePageCategoryServiceImpl extends ServiceImpl<LowCodePageCategoryMapper, LowCodePageCategory> implements LowCodePageCategoryService {
    @Override
    @Transactional
    public Result<LowCodePageCategory> addPageCategory(LowCodePageCategory lowCodePageCategory) {
        if (save(lowCodePageCategory)) {
            return Result.ok(lowCodePageCategory, "低代码页面分类-添加成功!");
        }
        return Result.fail(lowCodePageCategory, "错误:低代码页面分类-添加失败!");
    }

    @Override
    @Transactional
    public Result<LowCodePageCategory> editPageCategory(LowCodePageCategory lowCodePageCategory) {
        if (updateById(lowCodePageCategory)) {
            return Result.ok(lowCodePageCategory, "低代码页面分类-编辑成功!");
        }
        return Result.fail(lowCodePageCategory, "错误:低代码页面分类-编辑失败!");
    }

    @Override
    @Transactional
    public Result<Boolean> deletePageCategory(String id) {
        if (removeById(id)) {
            return Result.ok(true, "低代码页面分类-删除成功!");
        }
        return Result.fail(false, "错误:低代码页面分类-删除失败!");
    }

    @Override
    public Result<Boolean> deleteBatchPageCategory(String ids) {
        List<String> idArray = Arrays.asList(ids.split(","));
        if (removeByIds(idArray)) {
            return Result.ok(true, "低代码页面分类明细-批量删除成功!");
        }
        return Result.fail(false, "错误:低代码页面分类-批量删除失败!");
    }
}
