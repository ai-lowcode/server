package ${packageName}.controller;

import cn.com.axel.common.core.utils.excel.ExcelUtils;
import cn.com.axel.common.log.annotation.Log;
import cn.com.axel.common.oauth.annotation.RequiresPermissions;
import cn.com.axel.common.core.enums.OperateType;
import cn.com.axel.common.core.web.Result;
import ${packageName}.entity.${entityName};
import ${packageName}.req.Req${entityName};
import ${packageName}.service.${entityName}Service;
<#if searchList?size!=0>
import cn.com.axel.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
</#if>
import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;

/**
 * @description: ${tableInfo.tableComment}
 * @author: axel
 * @date: ${.now?string["yyyy-MM-dd"]}
 * @version: V1.3.1
 */
@Slf4j
@Tag(name = "${tableInfo.tableComment}")
@RestController
@RequestMapping("/${entityName?uncap_first}")
public class ${entityName}Controller {
	@Resource
	private ${entityName}Service ${entityName?uncap_first}Service;

	/**
	 * 分页列表查询
	 *
	 * @param req${entityName} ${tableInfo.tableComment}请求参数
	 * @param reqPage 分页参数
	 * @return 返回${tableInfo.tableComment}-分页列表
	 */
	@Operation(summary = "${tableInfo.tableComment}-分页列表查询", description = "${tableInfo.tableComment}-分页列表查询")
	@GetMapping
    @RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:query")
	public Result<PageResult<${entityName}>> queryPageList(Req${entityName} req${entityName}, ReqPage reqPage) {
		return Result.ok(new PageResult<>(queryList(req${entityName}, reqPage)), "${tableInfo.tableComment}-查询成功!");
	}

	/**
	* 获取列表
	*
	* @param req${entityName} ${tableInfo.tableComment}请求参数
	* @param reqPage 分页参数
	* @return 返回${tableInfo.tableComment}-分页列表
	*/
	private List<${entityName}> queryList(Req${entityName} req${entityName}, ReqPage reqPage) {
		PageHelper.startPage(reqPage.getPageNum(), reqPage.getPageSize());
	<#if searchList?size!=0>
		LambdaQueryWrapper<${entityName}> lambdaQueryWrapper = new LambdaQueryWrapper<${entityName}>()
		<#list searchList as search>
			<#if search.fieldInfo.type =='String'>
			.${search.condition}(!StringUtils.isEmpty(req${entityName}.get${search.field}()), ${entityName}::get${search.field}, req${entityName}.get${search.field}())
			<#else>
			.${search.condition}(null != req${entityName}.get${search.field}(), ${entityName}::get${search.field}, req${entityName}.get${search.field}())
			</#if>
		</#list>;
		return ${entityName?uncap_first}Service.list(lambdaQueryWrapper);
	<#else>
		return ${entityName?uncap_first}Service.list();
	</#if>
	}

	/**
	 * 添加
	 *
	 * @param ${entityName?uncap_first} ${tableInfo.tableComment}对象
	 * @return 返回${tableInfo.tableComment}-添加结果
	 */
	@Log(title = "${tableInfo.tableComment}-添加", operateType = OperateType.INSERT)
	@Operation(summary = "${tableInfo.tableComment}-添加")
	@PostMapping
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:insert")
	public Result<${entityName}> add(@RequestBody ${entityName} ${entityName?uncap_first}) {
		if (${entityName?uncap_first}Service.save(${entityName?uncap_first})) {
			return Result.ok(${entityName?uncap_first}, "${tableInfo.tableComment}-添加成功!");
		}
        return Result.fail(${entityName?uncap_first}, "错误:${tableInfo.tableComment}-添加失败!");
	}

	/**
	 * 编辑
	 *
	 * @param ${entityName?uncap_first} ${tableInfo.tableComment}对象
	 * @return 返回${tableInfo.tableComment}-编辑结果
	 */
	@Log(title = "${tableInfo.tableComment}-编辑", operateType = OperateType.UPDATE)
	@Operation(summary = "${tableInfo.tableComment}-编辑")
	@PutMapping
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:update")
	public Result<${entityName}> edit(@RequestBody ${entityName} ${entityName?uncap_first}) {
		if (${entityName?uncap_first}Service.updateById(${entityName?uncap_first})) {
		    return Result.ok(${entityName?uncap_first}, "${tableInfo.tableComment}-编辑成功!");
		}
		return Result.fail(${entityName?uncap_first}, "错误:${tableInfo.tableComment}-编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id 唯一ID
	 * @return 返回${tableInfo.tableComment}-删除结果
	 */
	@Log(title = "${tableInfo.tableComment}-通过id删除", operateType = OperateType.DELETE)
	@Operation(summary = "${tableInfo.tableComment}-通过id删除")
	@DeleteMapping("/{id}")
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:delete")
	public Result<Boolean> delete(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
		if (${entityName?uncap_first}Service.removeById(id)) {
			return Result.ok(true, "${tableInfo.tableComment}-删除成功!");
		}
		return Result.fail(false, "错误:${tableInfo.tableComment}-删除失败!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 批量ID
	 * @return 返回${tableInfo.tableComment}-删除结果
	 */
	@Log(title = "${tableInfo.tableComment}-批量删除", operateType = OperateType.DELETE)
	@Operation(summary = "${tableInfo.tableComment}-批量删除")
	@DeleteMapping("/batch/{ids}")
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:delete")
	public Result<Boolean> deleteBatch(@Parameter(name = "ids", description = "唯一性ID") @PathVariable String ids) {
		if (this.${entityName?uncap_first}Service.removeByIds(Arrays.asList(ids.split(",")))) {
		    return Result.ok(true, "${tableInfo.tableComment}-批量删除成功!");
		}
		return Result.fail(false, "错误:${tableInfo.tableComment}-批量删除失败!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id 唯一ID
	 * @return 返回${tableInfo.tableComment}对象
	 */
	@Operation(summary = "${tableInfo.tableComment}-通过id查询")
	@GetMapping("/{id}")
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:query")
	public Result<${entityName}> queryById(@Parameter(name = "id", description = "唯一性ID") @PathVariable String id) {
		${entityName} ${entityName?uncap_first} = ${entityName?uncap_first}Service.getById(id);
		return Result.ok(${entityName?uncap_first}, "${tableInfo.tableComment}-查询成功!");
	}

	/**
	* 导出
	* @param req${entityName} ${tableInfo.tableComment}请求参数
	* @param reqPage 分页参数
	* @throws IOException IO异常
	*/
	@Operation(summary = "导出${tableInfo.tableComment}", description = "导出${tableInfo.tableComment}")
	@GetMapping("/export")
	@RequiresPermissions("${apiPrefix}:${entityName?uncap_first}:export")
	public void export(Req${entityName} req${entityName}, ReqPage reqPage) throws IOException {
		//swagger调用会用问题，使用postman测试
		<#if tableInfo.tableComment??>
		ExcelUtils.write("${entityName}", queryList(req${entityName}, reqPage));
			<#else>
		ExcelUtils.write("${tableInfo.tableComment}", queryList(req${entityName}, reqPage));
		</#if>
	}
}