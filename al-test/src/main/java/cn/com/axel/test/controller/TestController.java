package cn.com.axel.test.controller;


import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.remote.RemoteUserService;
import cn.com.axel.common.oauth.api.vo.UserInfoVo;
import cn.com.axel.test.entity.TestParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: axel
 * @description: Axel测试中心
 * @date: 2021/12/3 17:12
 */
@RestController
@Tag(name = "测试接口")
public class TestController {
    @Resource
    RemoteUserService remoteUserService;

    @GetMapping("/user")
    public Result<UserInfoVo> getUserInfo(HttpServletRequest request) {
        String token = AuthInfoUtils.getAccessToken(request);
        return remoteUserService.getUserInfo(RPCConstants.INNER, token);
    }

    @GetMapping("/curUser")
    public Result<UserInfo> getCurUserInfo() {
        return remoteUserService.getUserById(RPCConstants.INNER, "1");
    }

    @PostMapping("/testParam")
    public Result<TestParam> testParam(@RequestBody TestParam param) {
        return Result.ok(param);
    }
}
