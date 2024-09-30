package ${packageName}.service.impl;

import ${packageName}.entity.${entityName};
import ${packageName}.mapper.${entityName}Mapper;
import ${packageName}.service.${entityName}Service;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: ${tableInfo.tableComment}
 * @author: axel
 * @date: ${.now?string["yyyy-MM-dd"]}
 * @version: V1.3.1
 */
@Service
public class ${entityName}ServiceImpl extends ServiceImpl<${entityName}Mapper, ${entityName}> implements ${entityName}Service {

}
