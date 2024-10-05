package cn.com.axel.lowcode.service.impl;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.lowcode.entity.LowCodePage;
import cn.com.axel.lowcode.mapper.LowCodePageMapper;
import cn.com.axel.lowcode.service.LowCodePageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 低代码页面
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Service
public class LowCodePageServiceImpl extends ServiceImpl<LowCodePageMapper, LowCodePage> implements LowCodePageService {
    @Override
    @Transactional
    public Result<LowCodePage> addPage(LowCodePage lowCodePage) {
        if (save(lowCodePage)) {
            return Result.ok(lowCodePage, "低代码页面-添加成功!");
        }
        return Result.fail(lowCodePage, "错误:低代码页面-添加失败!");
    }

    @Override
    @Transactional
    public Result<LowCodePage> editPage(LowCodePage lowCodePage) {
        if (updateById(lowCodePage)) {
            return Result.ok(lowCodePage, "低代码页面-编辑成功!");
        }
        return Result.fail(lowCodePage, "错误:低代码页面-编辑失败!");
    }

    @Override
    @Transactional
    public Result<Boolean> deletePage(String id) {
        if (removeById(id)) {
            return Result.ok(true, "低代码页面-删除成功!");
        }
        return Result.fail(false, "错误:低代码页面-删除失败!");
    }

    @Override
    public Result<Boolean> deleteBatchPage(String ids) {
        List<String> idArray = Arrays.asList(ids.split(","));
        if (removeByIds(idArray)) {
            return Result.ok(true, "低代码页面明细-批量删除成功!");
        }
        return Result.fail(false, "错误:低代码页面-批量删除失败!");
    }
}
