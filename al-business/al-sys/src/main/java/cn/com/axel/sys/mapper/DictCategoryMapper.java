package cn.com.axel.sys.mapper;

import cn.com.axel.sys.entity.DictCategory;
import cn.com.axel.sys.req.ReqDictCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 树形分类
 * @author: axel
 * @date: 2024-03-12
 * @version: V0.0.1
 */
public interface DictCategoryMapper extends BaseMapper<DictCategory> {
    int insertCategory(DictCategory dictCategory);

    Integer queryMaxCategoryLevel(@Param("reqDictCategory") ReqDictCategory reqDictCategory);

    List<DictCategory> queryCategory(@Param("reqDictCategory") ReqDictCategory reqDictCategory, @Param("levels") List<Integer> levels);

    /**
     * 查询1级分类列表
     *
     * @param reqDictCategory 过滤参数
     * @param levels          所有父级等级
     * @return 返回1级分类列表
     */
    List<DictCategory> queryOneLevelCategory(@Param("reqDictCategory") ReqDictCategory reqDictCategory, @Param("levels") List<Integer> levels);

    /**
     * 查询子分类包含自己
     *
     * @param list treeCode列表
     * @return 返回子分类列表
     */
    List<DictCategory> queryChildCategory(@Param("reqDictCategory") ReqDictCategory reqDictCategory, @Param("levels") List<Integer> levels, @Param("list") List<String> list);
}
