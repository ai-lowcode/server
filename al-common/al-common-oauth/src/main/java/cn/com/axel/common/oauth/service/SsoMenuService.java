package cn.com.axel.common.oauth.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoMenu;
import cn.com.axel.common.oauth.req.ReqSsoMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 菜单权限表
 * @Author: axel
 * @date: 2022-09-21
 * @Version: V1.3.1
 */
public interface SsoMenuService extends IService<SsoMenu> {
    Result<SsoMenu> insertMenu(SsoMenu ssoMenu);

    Result<List<SsoMenu>> queryMenuTree(ReqSsoMenu reqSsoMenu, String userId);

    List<SsoMenu> queryMenu(ReqSsoMenu reqSsoMenu, String userId);

    Result<SsoMenu> updateMenu(SsoMenu ssoMenu);

    Result<Boolean> deleteMenu(String menuId);

    Result<Boolean> routeExist(String routePath, String parentId);
}