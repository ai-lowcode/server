package cn.com.axel.lowcode.entity;

import cn.com.axel.common.core.entity.BaseEntity;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 低代码页面分类
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Data
@TableName("lowcode_page_category")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "lowcode_page_category对象 低代码页面分类")
public class LowCodePageCategory extends BaseEntity<String> {
    @ExcelProperty("唯一ID")
    @Schema(description = "唯一ID")
    @TableId(type = IdType.ASSIGN_UUID)
    @Accessors(chain = true)
    private String id;
    @ExcelProperty("分类名称")
    @Schema(description = "分类名称")
	private String name;
    @ExcelProperty("分类标识")
    @Schema(description = "分类标识")
	private String slug;
    @ExcelProperty("分类描述")
    @Schema(description = "分类描述")
    private String describe;
    @ExcelProperty("父级id")
    @Schema(description = "父级id")
    private String parentId;
    @ExcelProperty("分类状态")
    @Schema(description = "分类状态")
    private Short status;
}
