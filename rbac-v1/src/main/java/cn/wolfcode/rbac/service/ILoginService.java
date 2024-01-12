package cn.wolfcode.rbac.service;

import cn.wolfcode.rbac.domain.Employee;
import cn.wolfcode.rbac.domain.vo.LoginInfoVo;
import cn.wolfcode.rbac.domain.vo.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 */
public interface ILoginService {
     Employee login(LoginInfoVo loginInfoVo,HttpServletRequest request) throws Exception;
     Map<String,String> verifyCode();
     void logout(String userId);
}
