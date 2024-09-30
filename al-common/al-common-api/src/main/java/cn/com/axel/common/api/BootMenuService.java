package cn.com.axel.common.api;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoMenu;
import cn.com.axel.common.oauth.api.remote.RemoteMenuService;
import cn.com.axel.common.oauth.service.SsoMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @description: 菜单远程接口的本地实现
 * @author: axel
 * @date: 2024/4/22
 */
@Service("remoteMenuService")
public class BootMenuService implements RemoteMenuService {
    @Resource
    SsoMenuService ssoMenuService;

    @Override
    public Result<SsoMenu> add(String origin, SsoMenu ssoMenu) {
        return ssoMenuService.insertMenu(ssoMenu);
    }

    @Override
    public Result<Boolean> routeExist(String routePath, String parentId) {
        return ssoMenuService.routeExist(routePath, parentId);
    }
}
