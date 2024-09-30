package cn.com.axel.common.code.entity;

import cn.com.axel.common.code.req.ReqSearch;
import cn.com.axel.sys.api.entity.FieldInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 搜索条件
 * @author: axel
 * @date: 2023/5/9 22:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(description = "搜索条件增加字段信息")
public class SearchInfo extends ReqSearch {
    @Schema(description = "字段信息")
    private FieldInfo fieldInfo;
}
