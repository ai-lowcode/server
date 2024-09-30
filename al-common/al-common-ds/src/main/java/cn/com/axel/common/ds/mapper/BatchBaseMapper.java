package cn.com.axel.common.ds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 批量新增mapper
 * @author: axel
 * @date: 2024/3/26
 */
@Mapper
public interface BatchBaseMapper<T> extends BaseMapper<T> {
    int insertBatchSomeColumn(List<T> list);
}
