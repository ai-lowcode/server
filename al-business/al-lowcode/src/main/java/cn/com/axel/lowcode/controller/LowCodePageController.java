package cn.com.axel.lowcode.controller;

import cn.com.axel.common.core.enums.OperateType;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.log.annotation.Log;
import cn.com.axel.lowcode.entity.LowCodePage;
import cn.com.axel.lowcode.req.ReqLowCodePage;
import cn.com.axel.lowcode.service.LowCodePageService;
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
@Tag(name = "低代码页面")
@RestController
@RequestMapping("/lowCodePage")
public class LowCodePageController {
    @Resource
    private LowCodePageService lowCodePageService;

    /**
     * 分页列表查询
     *
     * @param reqLowCodePage 低代码页面请求参数
     * @param reqPage      分页参数
     * @return 返回低代码页面-分页列表
     */
    @Operation(summary = "低代码页面-分页列表查询", description = "低代码页面-分页列表查询")
    @GetMapping
//    @RequiresPermissions("lowCode:lowCodePage:query")
    public Result<PageResult<LowCodePage>> queryPageList(ReqLowCodePage reqLowCodePage, ReqPage reqPage) {
        return Result.ok(new PageResult<>(queryList(reqLowCodePage, reqPage)), "低代码页面-查询成功!");
    }

    /**
     * 获取列表
     *
     * @param reqLowCodePage 低代码页面请求参数
     * @param reqPage      分页参数
     * @return 返回低代码页面-分页列表
     */
    private List<LowCodePage> queryList(ReqLowCodePage reqLowCodePage, ReqPage reqPage) {
        PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
        LambdaQueryWrapper<LowCodePage> lambdaQueryWrapper = new LambdaQueryWrapper<LowCodePage>()
                .like(!StringUtils.isEmpty(reqLowCodePage.getPageName()), LowCodePage::getPageName, reqLowCodePage.getPageName())
                .eq(null != reqLowCodePage.getPageStatus(), LowCodePage::getPageStatus, reqLowCodePage.getPageStatus())
                .eq(null != reqLowCodePage.getPageSlug(), LowCodePage::getPageSlug, reqLowCodePage.getPageSlug());
        return lowCodePageService.list(lambdaQueryWrapper);
    }

    /**
     * 添加
     *
     * @param lowCodePage 低代码页面对象
     * @return 返回低代码页面-添加结果
     */
    @Log(title = "低代码页面-添加", operateType = OperateType.INSERT)
    @Operation(summary = "低代码页面-添加")
    @PostMapping
//    @RequiresPermissions("lowCode:lowCodePage:insert")
    public Result<LowCodePage> add(@RequestBody LowCodePage lowCodePage) {
        return lowCodePageService.addPage(lowCodePage);
    }

    /**
     * 编辑
     *
     * @param lowCodePage 低代码页面对象
     * @return 返回低代码页面-编辑结果
     */
    @Log(title = "低代码页面-编辑", operateType = OperateType.UPDATE)
    @Operation(summary = "低代码页面-编辑")
    @PutMapping
//    @RequiresPermissions("lowCode:lowCodePage:update")
    public Result<LowCodePage> edit(@RequestBody LowCodePage lowCodePage) {
        // 假设我们要替换 content 字段中的某些字符
        String originalContent = lowCodePage.getPageContent();

        // 使用 replace 方法进行替换
        String updatedContent = originalContent.replace("&gt;", ">");

        // 将更新后的内容设置回 lowCodePage 对象
        lowCodePage.setPageContent(updatedContent);
        System.out.print(lowCodePage);

        return lowCodePageService.editPage(lowCodePage);
    }

    /**
     * 通过id删除
     *
     * @param id 唯一ID
     * @return 返回低代码页面-删除结果
     */
    @Log(title = "低代码页面-通过id删除", operateType = OperateType.DELETE)
    @Operation(summary = "低代码页面-通过id删除")
    @DeleteMapping("/{id}")
//    @RequiresPermissions("lowCode:lowCodePage:delete")
    public Result<Boolean> delete(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
        return lowCodePageService.deletePage(id);
    }

    /**
     * 批量删除
     *
     * @param ids 批量ID
     * @return 返回低代码页面-删除结果
     */
    @Log(title = "低代码页面-批量删除", operateType = OperateType.DELETE)
    @Operation(summary = "低代码页面-批量删除")
    @DeleteMapping("/batch/{ids}")
//    @RequiresPermissions("lowCode:lowCodePage:delete")
    public Result<Boolean> deleteBatch(@Parameter(name = "ids", description = "唯一性ID") @PathVariable String ids) {
        return lowCodePageService.deleteBatchPage(ids);
    }

    /**
     * 通过id查询
     *
     * @param id 唯一ID
     * @return 返回低代码页面对象
     */
    @Operation(summary = "低代码页面-通过id查询")
    @GetMapping("/{id}")
//    @RequiresPermissions("lowCode:lowCodePage:query")
    public Result<LowCodePage> queryById(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
        LowCodePage lowCodePage = lowCodePageService.getById(id);
        return Result.ok(lowCodePage, "低代码页面-查询成功!");
    }
}
