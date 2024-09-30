package cn.com.axel.demo.service.impl;

import cn.com.axel.demo.entity.DemoImportExport;
import cn.com.axel.demo.mapper.DemoImportExportMapper;
import cn.com.axel.demo.service.DemoImportExportService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 导入导出Demo
 * @author: axel
 * @date: 2024-09-02
 * @version: V1.3.1
 */
@Service
public class DemoImportExportServiceImpl extends ServiceImpl<DemoImportExportMapper, DemoImportExport> implements DemoImportExportService {

    @Override
    public int insertBatchSomeColumn(List<DemoImportExport> list) {
        return baseMapper.insertBatchSomeColumn(list);
    }
}
