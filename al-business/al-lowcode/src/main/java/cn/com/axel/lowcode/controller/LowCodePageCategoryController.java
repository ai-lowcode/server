package cn.com.axel.lowcode.controller;

import cn.com.axel.common.core.enums.OperateType;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.log.annotation.Log;
import cn.com.axel.common.oauth.annotation.RequiresPermissions;
import cn.com.axel.lowcode.entity.LowCodePageCategory;
import cn.com.axel.lowcode.req.ReqLowCodePageCategory;
import cn.com.axel.lowcode.service.LowCodePageCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: lowcode_data_scope
 * @author: axel
 * @date: 2024-09-04
 * @version: V0.0.1
 */
@Slf4j
@Tag(name = "低代码页面分类")
@RestController
@RequestMapping("/lowCodePageCategory")
public class LowCodePageCategoryController {
    @Resource
    private LowCodePageCategoryService lowCodePageCategoryService;

    /**
     * 分页列表查询
     *
     * @param reqLowCodePageCategory 低代码页面分类请求参数
     * @param reqPage      分页参数
     * @return 返回低代码页面分类-分页列表
     */
    @Operation(summary = "低代码页面分类-分页列表查询", description = "低代码页面分类-分页列表查询")
    @GetMapping
//    @RequiresPermissions("lowCode:lowCodePageCategory:query")
    public Result<PageResult<LowCodePageCategory>> queryPageList(ReqLowCodePageCategory reqLowCodePageCategory, ReqPage reqPage) {
        return Result.ok(new PageResult<>(queryList(reqLowCodePageCategory, reqPage)), "低代码页面分类-查询成功!");
    }

    /**
     * 获取列表
     *
     * @param reqLowCodePageCategory 低代码页面分类请求参数
     * @param reqPage      分页参数
     * @return 返回低代码页面分类-分页列表
     */
    private List<LowCodePageCategory> queryList(ReqLowCodePageCategory reqLowCodePageCategory, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        LambdaQueryWrapper<LowCodePageCategory> lambdaQueryWrapper = new LambdaQueryWrapper<LowCodePageCategory>()
                .like(!StringUtils.isEmpty(reqLowCodePageCategory.getName()), LowCodePageCategory::getName, reqLowCodePageCategory.getName())
                .eq(null != reqLowCodePageCategory.getStatus(), LowCodePageCategory::getStatus, reqLowCodePageCategory.getStatus())
                .eq(null != reqLowCodePageCategory.getSlug(), LowCodePageCategory::getSlug, reqLowCodePageCategory.getSlug());
        return lowCodePageCategoryService.list(lambdaQueryWrapper);
    }

    /**
     * 添加
     *
     * @param lowCodePageCategory 低代码页面分类对象
     * @return 返回低代码页面分类-添加结果
     */
    @Log(title = "低代码页面分类-添加", operateType = OperateType.INSERT)
    @Operation(summary = "低代码页面分类-添加")
    @PostMapping
//    @RequiresPermissions("lowCode:lowCodePageCategory:insert")
    public Result<LowCodePageCategory> add(@RequestBody LowCodePageCategory lowCodePageCategory) {
        return lowCodePageCategoryService.addPageCategory(lowCodePageCategory);
    }

    /**
     * 编辑
     *
     * @param lowCodePageCategory 低代码页面分类对象
     * @return 返回低代码页面分类-编辑结果
     */
    @Log(title = "低代码页面分类-编辑", operateType = OperateType.UPDATE)
    @Operation(summary = "低代码页面分类-编辑")
    @PutMapping
//    @RequiresPermissions("lowCode:lowCodePageCategory:update")
    public Result<LowCodePageCategory> edit(@RequestBody LowCodePageCategory lowCodePageCategory) {
        return lowCodePageCategoryService.editPageCategory(lowCodePageCategory);
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回低代码页面分类-删除结果
     */
    @Log(title = "低代码页面分类-通过id删除", operateType = OperateType.DELETE)
    @Operation(summary = "低代码页面分类-通过id删除")
    @DeleteMapping("/{id}")
//    @RequiresPermissions("lowCode:lowCodePageCategory:delete")
    public Result<Boolean> delete(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
        return lowCodePageCategoryService.deletePageCategory(id);
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回低代码页面分类-删除结果
     */
    @Log(title = "低代码页面分类-批量删除", operateType = OperateType.DELETE)
    @Operation(summary = "低代码页面分类-批量删除")
    @DeleteMapping("/batch/{ids}")
//    @RequiresPermissions("lowCode:lowCodePageCategory:delete")
    public Result<Boolean> deleteBatch(@Parameter(name = "ids", description = "唯一性ID") @PathVariable String ids) {
        return lowCodePageCategoryService.deleteBatchPageCategory(ids);
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回低代码页面分类对象
     */
    @Operation(summary = "低代码页面分类-通过id查询")
    @GetMapping("/{id}")
//    @RequiresPermissions("lowCode:lowCodePageCategory:query")
    public Result<LowCodePageCategory> queryById(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
        LowCodePageCategory lowCodePageCategory = lowCodePageCategoryService.getById(id);
        return Result.ok(lowCodePageCategory, "低代码页面分类-查询成功!");
    }
}
