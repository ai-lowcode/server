package cn.com.axel.lowcode.entity;

import cn.com.axel.common.core.entity.BaseEntity;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 低代码页面
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
@Data
@TableName("lowcode_page")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "lowcode_page对象 低代码页面")
public class LowCodePage extends BaseEntity<String> {
    @ExcelProperty("唯一ID")
    @Schema(description = "唯一ID")
    @TableId(type = IdType.ASSIGN_UUID)
    @Accessors(chain = true)
    private String id;
    @ExcelProperty("页面名称")
    @Schema(description = "页面名称")
	private String pageName;
    @ExcelProperty("页面标识")
    @Schema(description = "页面标识")
	private String pageSlug;
    @ExcelProperty("页面描述")
    @Schema(description = "页面描述")
    private String pageDescribe;
    @ExcelProperty("页面内容")
    @Schema(description = "页面内容")
    private String pageContent;
    @ExcelProperty("页面分类")
    @Schema(description = "页面分类")
    private String pageCategory;
    @ExcelProperty("页面状态")
    @Schema(description = "页面状态")
    private Short pageStatus;
}
