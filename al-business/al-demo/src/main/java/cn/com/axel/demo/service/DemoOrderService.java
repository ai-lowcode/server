package cn.com.axel.demo.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.demo.entity.DemoOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 销售订单
 * @author: axel
 * @date: 2024-09-13
 * @version: V0.0.1
 */
public interface DemoOrderService extends IService<DemoOrder> {
    Result<DemoOrder> addOrder(DemoOrder demoOrder);
    Result<DemoOrder> editOrder(DemoOrder demoOrder);
    Result<Boolean> deleteOrder(String id);
    Result<Boolean> deleteBatchOrder(String ids);
}
