package cn.com.axel.demo.service;

import cn.com.axel.demo.entity.DemoImportExport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 导入导出Demo
 * @author: axel
 * @date: 2024-09-02
 * @version: V1.3.1
 */
public interface DemoImportExportService extends IService<DemoImportExport> {
    int insertBatchSomeColumn(List<DemoImportExport> list);
}
