package cn.com.axel.oauth.service.impl;

import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.core.exception.CaptchaException;
import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.common.SerConstant;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.common.oauth.service.SsoUserService;
import cn.com.axel.common.redis.common.RedisPrefix;
import cn.com.axel.oauth.common.MyUsernamePasswordToken;
import cn.com.axel.oauth.entity.OAuthClient;
import cn.com.axel.oauth.oltu.common.OAuth;
import cn.com.axel.oauth.service.LoginService;
import cn.com.axel.oauth.validator.GetCodeValidator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: axel
 * @date: 2020/2/15 16:10
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    SsoUserService ssoUserService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    GetCodeValidator getCodeValidator;
    //允许连续出错时间间隔的最大错误数
    final static int ERROR_COUNT = 5;
    //允许连续出错的时间间隔 单位:分钟  30分钟内不允许连续出错5次
    final static long ERROR_TIME_INTERVAL = 30;

    @Override
    public boolean getLogin(Model model, HttpServletRequest request) {
        //校验当前请求code相关参数是否正确
        validateCode(model, request);
        //get的登录请求，始终返回登录页
        return false;
    }

    @Override
    public boolean postLogin(Model model, HttpServletRequest request) {
        String captchaEx = request.getHeader(RPCConstants.REQ_CHECK_CAPTCHA_EXCEPTION);
        if (!StringUtils.isEmpty(captchaEx)) {
            model.addAttribute(SerConstant.ERROR_MSG, CaptchaException.Info.getExceptionInfo(captchaEx).toString());
            return false;
        }
        if (!validateCode(model, request)) {
            return false;
        }
        return login(model, request);
    }

    /**
     * 请求code参数校验
     * <p>
     * 本方法主要用于校验授权码（code）的有效性通过HTTP请求参数进行校验如果校验失败，
     * 会将错误信息添加到模型中并返回false否则，返回true表示校验成功
     *
     * @param model 用于存储属性值，在发生错误时用于存储错误信息
     * @param request HTTP请求对象，用于获取请求参数
     * @return boolean 校验是否成功
     */
    private boolean validateCode(Model model, HttpServletRequest request) {
        Result<OAuthClient> result = getCodeValidator.validateClient(request, null);
        if (!result.isSuccess()) {
            model.addAttribute(SerConstant.ERROR_MSG, result.getMsg());
            return false;
        }
        return true;
    }

    /**
     * web请求登录 构建model返回值
     *
     * @param model 用于存储属性值，在发生错误时用于存储错误信息
     * @param request HTTP请求对象，用于获取请求参数
     * @return boolean 校验是否成功
     */
    public boolean login(Model model, HttpServletRequest request) {
        Result<String> result = login(request);
        for (Map.Entry<String, String> entry : result.getParam().entrySet()) {
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return result.isSuccess();
    }

    /**
     * 登录用户验证逻辑
     *
     * @param request HTTP请求对象，用于获取请求参数
     * @return 返回登陆结果
     */
    @Override
    public Result<String> login(HttpServletRequest request) {
        String username = request.getParameter(OAuth.OAUTH_USERNAME);
        String password = request.getParameter(OAuth.OAUTH_PASSWORD);
        SerConstant.LoginType loginType = SerConstant.LoginType.getLoginType(request.getParameter(SerConstant.LOGIN_TYPE));
        String rememberMe = request.getParameter(SerConstant.REMEMBER_ME);
        String clientId = request.getParameter(SerConstant.CLIENT_ID);
        return login(username, password, loginType, clientId, rememberMe);

    }


    /**
     * 登录用户验证逻辑
     *
     * @param username 账号
     * @param password 密码
     * @param loginType 登陆类型
     * @return 返回登陆结果
     */
    public Result<String> login(String username, String password, SerConstant.LoginType loginType, String clientId, String rememberMe) {
        boolean remember = false;
        if (!StringUtils.isEmpty(rememberMe)) {
            remember = Boolean.parseBoolean(rememberMe);
        }
        Result<String> result = new Result<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.setSuccess(false).setMsg(SerConstant.INVALID_USER_SECRET_DESCRIPTION)
                    .getParam().put(SerConstant.ERROR_MSG, SerConstant.INVALID_USER_SECRET_DESCRIPTION);
            return result;
        }
        MyUsernamePasswordToken token = new MyUsernamePasswordToken(username, password, remember)
                .setLoginType(loginType).setClientId(clientId);
        try {
            SecurityUtils.getSubject().login(token);
            return result;
        } catch (IncorrectCredentialsException ex) {
            //错误凭证错误信息
            result.setSuccess(false).setMsg(ex.getMessage()).getParam().put(SerConstant.ERROR_MSG, ex.getMessage());
            log.info("用户:{}登录客户端:{}凭证错误{}", username, clientId, ex.getMessage(), ex);
            return result;
        } catch (Exception ex) {
            //其他异常错误信息
            result.setSuccess(false).setMsg(ex.getMessage()).getParam().put(SerConstant.ERROR_MSG, ex.getMessage());
            log.info("用户:{}登录客户端:{}异常{}", username, clientId, ex.getMessage(), ex);
            return result;
        } finally {
            result.setData(token.getUserInfo() != null ? token.getUserInfo().getId() : null);
            result.getParam().put(OAuth.OAUTH_USERNAME, username);
            result.getParam().put(SerConstant.LOGIN_TYPE, loginType.toString());
        }
    }

    @Override
    public boolean retryLimit(String userId, boolean matches) {
        SsoUser user = ssoUserService.getUserById(userId);
        if (user == null) {
            log.error("{}" + SerConstant.INVALID_USER_ID_DESCRIPTION, userId);
            throw new IncorrectCredentialsException(SerConstant.INVALID_USER_ID_DESCRIPTION);
        }
        //超户不允许禁用、锁定、删除
        if (!AuthInfoUtils.isSuper(userId)) {
            if (SerConstant.AccountState.禁用.getValue() == user.getStatus()) {
                log.error("{}" + SerConstant.ACCOUNT_DISABLE_DESCRIPTION, userId);
                throw new IncorrectCredentialsException(SerConstant.ACCOUNT_DISABLE_DESCRIPTION);
            }
            if (user.getDelFlag().equals(1)) {
                log.error("{}" + SerConstant.ACCOUNT_DELETE_DESCRIPTION, userId);
                throw new IncorrectCredentialsException(SerConstant.ACCOUNT_DELETE_DESCRIPTION);
            }
        }
        int count = getLoginCount(userId);
        if (matches) {
            //清空重试次数
            removeLoginCount(userId);
            return true;
        }
        if (count >= ERROR_COUNT) {
            String error = MessageFormat.format("{0},连续输错5次密码，账号禁用"
                    , SerConstant.INVALID_USER_SECRET_DESCRIPTION);
            user.setStatus(SerConstant.AccountState.禁用.getValue());
            ssoUserService.updateUser(user);
            log.error("{}{}", userId, error);
            //规定时间内重试ERROR_COUNT次，抛出多次尝试异常
            throw new ExcessiveAttemptsException(error);
        }
        String error = MessageFormat.format("{0},连续出错{1}次,错误{2}次将被禁用"
                , SerConstant.INVALID_USER_SECRET_DESCRIPTION, count, ERROR_COUNT);
        log.error("{}{}", userId, error);
        throw new IncorrectCredentialsException(error);
    }

    @Override
    public void sendMsg(String phone, String msg) {
        //TODO 根据具体短信网关实现
    }

    @Override
    public void saveSmsCode(String phone, String code) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSMSCodeKey(phone), code, 5, TimeUnit.MINUTES);
    }

    @Override
    public void delSmsCode(String phone) {
        redisTemplate.delete(RedisPrefix.buildSMSCodeKey(phone));
    }

    @Override
    public String getSmsCode(String phone) {
        return (String) redisTemplate.opsForValue().get(RedisPrefix.buildSMSCodeKey(phone));
    }

    @Override
    public void saveSmsCodeTime(String phone) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSMSCodeTimeKey(phone), "", 1, TimeUnit.MINUTES);
    }

    @Override
    public void delSmsCodeTime(String phone) {
        redisTemplate.delete(RedisPrefix.buildSMSCodeTimeKey(phone));
    }

    @Override
    public Long getSmsCodeTime(String phone) {
        return redisTemplate.getExpire(RedisPrefix.buildSMSCodeTimeKey(phone));
    }

    @Override
    public void sessionKeyTempCache(String sessionKey, String openId) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSessionKey(sessionKey), openId, 5, TimeUnit.MINUTES);
    }

    @Override
    public String getOpenIdBySessionKey(String sessionKey) {
        return (String) redisTemplate.opsForValue().get(RedisPrefix.buildSessionKey(sessionKey));
    }

    /**
     * 获取30分钟内登录次数
     *
     * @param userId 用户id
     * @return 次数
     */
    public int getLoginCount(String userId) {
        RedisAtomicLong ral = new RedisAtomicLong(RedisPrefix.buildLoginCountKey(userId)
                , Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        ral.incrementAndGet();
        //第一次设置允许错误的时间间隔
        if (ral.intValue() == 1) {
            ral.expire(ERROR_TIME_INTERVAL, TimeUnit.MINUTES);
        }
        return ral.intValue();
    }

    /**
     * 移除登录次数
     *
     * @param userId 用户id
     */
    public void removeLoginCount(String userId) {
        redisTemplate.delete(RedisPrefix.buildLoginCountKey(userId));
    }
}
