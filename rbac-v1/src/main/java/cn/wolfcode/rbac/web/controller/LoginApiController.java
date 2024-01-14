package cn.wolfcode.rbac.web.controller;

import cn.wolfcode.rbac.common.Constants;
import cn.wolfcode.rbac.domain.Employee;
import cn.wolfcode.rbac.domain.vo.LoginInfoVo;
import cn.wolfcode.rbac.domain.vo.R;
import cn.wolfcode.rbac.service.ILoginService;
import cn.wolfcode.rbac.utils.ObsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 处理登录请求的Controller
 */
@Controller
@RequestMapping("/api")
@CrossOrigin(allowCredentials="true")
public class LoginApiController {

    @Autowired
    private ILoginService   loginService;

    //验证码请求
    @GetMapping("code")
    @ResponseBody
    public R verifyCode(){
        Map<String, String> map = loginService.verifyCode();
        return R.ok(map);
    }

    //登录请求
    @PostMapping("login")
    @ResponseBody
    public R login(@RequestBody LoginInfoVo loginInfoVo,HttpServletRequest request) throws Exception {
        Employee loginEmployee = loginService.login(loginInfoVo,request);
        return R.ok("登录成功",loginEmployee);
    }

    //登录请求
    @GetMapping("logout")
    @ResponseBody
    public R logout(HttpServletRequest request)  {
        loginService.logout(request.getHeader(Constants.USER_ID));
        return R.ok("退出成功");
    }
}
