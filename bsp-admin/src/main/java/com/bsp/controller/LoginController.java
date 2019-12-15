package com.bsp.controller;

import com.bsp.entity.Administrator;
import com.bsp.enums.BussCode;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Cryptography;
import com.bsp.utils.Result;
import com.bsp.vo.AdministratorVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope(value = "prototype")
public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 获取登录用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/token")
    public Result getToken() {
        Administrator administrator = null;
        try {
            administrator = (Administrator) ShiroUtils.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ShiroUtils.getSession().getId() + "获取登录信息失败:" + e.getMessage());
            return Result.error("系统错误，获取登录信息失败");
        }
        AdministratorVO vo = new AdministratorVO();
        Result result = Result.success();
        BeanUtils.copyProperties(administrator, vo);
        result.put("token", vo);
        return result;
    }

    /**
     * 管理员登录
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, @RequestParam("username") String username,
                        @RequestParam("password") String password, @RequestParam("vcode") String vcode) {
        if (!vcode.equalsIgnoreCase((String) request.getSession().getAttribute("session_vcode"))) {
            return Result.error(BussCode.NOT_LOGIN, "验证码错误");
        }
        Subject subject = ShiroUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    Cryptography.MD5Hash(password, username));
            subject.login(token);
        } catch (UnknownAccountException e) {
            return Result.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return Result.error(BussCode.NOT_LOGIN, "用户名或密码不正确");
        } catch (LockedAccountException e) {
            return Result.error(e.getMessage());
        } catch (AuthenticationException e) {
            return Result.error("账户验证失败");
        } catch (SystemErrorException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error(BussCode.ERR_UNKNOWN, "系统错误");
        }
        Result result = Result.success();
        if (WebUtils.getSavedRequest(request) != null) {// 登录前访问的url
            result.put("url", WebUtils.getSavedRequest(request).getRequestUrl());
        }
        logger.info(((Administrator) ShiroUtils.getToken()).getaId() + "登录系统");
        return result;
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public Result logout() {
        try {
            ShiroUtils.logout();
        } catch (Exception e) {
            logger.error("登出失败:" + e.getMessage());
            return Result.error(BussCode.ERR_UNKNOWN, "系统异常，登出失败");
        }
        return Result.success();
    }

}
