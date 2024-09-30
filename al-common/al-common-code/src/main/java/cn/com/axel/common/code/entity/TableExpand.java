package cn.com.axel.common.code.entity;

import cn.com.axel.sys.api.entity.TableInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description: 表拓展
 * @author: axel
 * @date: 2024/4/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TableExpand extends TableInfo {
    @Schema(description = "列信息")
    private List<FieldExpand> fieldExpands;
}
