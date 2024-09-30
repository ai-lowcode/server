package cn.com.axel.common.ds.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: SQL注入器 支持批量新增
 * @author: axel
 * @date: 2024/3/26
 */
@Component
public class BatchSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        // 注入InsertBatchSomeColumn
        // 在!t.isLogicDelete()表示不要逻辑删除字段
        methodList.add(new InsertBatchSomeColumn(t -> !t.isLogicDelete()));
        return methodList;
    }
}
