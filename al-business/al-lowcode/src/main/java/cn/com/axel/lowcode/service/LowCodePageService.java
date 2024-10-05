package cn.com.axel.lowcode.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.lowcode.entity.LowCodePage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 低代码页面
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
public interface LowCodePageService extends IService<LowCodePage> {
    Result<LowCodePage> addPage(LowCodePage lowCodePage);
    Result<LowCodePage> editPage(LowCodePage lowCodePage);
    Result<Boolean> deletePage(String id);
    Result<Boolean> deleteBatchPage(String ids);
}
