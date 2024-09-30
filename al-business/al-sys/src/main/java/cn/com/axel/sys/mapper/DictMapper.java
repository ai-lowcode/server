package cn.com.axel.sys.mapper;

import cn.com.axel.sys.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 字典
 * @Author: axel
 * @date: 2023-01-03
 * @Version: V1.3.1
 */
public interface DictMapper extends BaseMapper<Dict> {
    Integer isDictCodeExist(@Param("id") String id, @Param("dictCode") String dictCode);
}
