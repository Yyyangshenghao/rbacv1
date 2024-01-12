package cn.wolfcode.rbac.service.impl;

import cn.wolfcode.rbac.common.Constants;
import cn.wolfcode.rbac.domain.Employee;
import cn.wolfcode.rbac.domain.vo.LoginInfoVo;
import cn.wolfcode.rbac.mapper.EmployeeMapper;
import cn.wolfcode.rbac.mapper.PermissionMapper;
import cn.wolfcode.rbac.service.ILoginService;
import cn.wolfcode.rbac.utils.RedisUtils;
import cn.wolfcode.rbac.utils.VerifyCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public Employee login(LoginInfoVo loginInfoVo, HttpServletRequest request) throws Exception {
        // 1 非法参数
        Assert.notNull(loginInfoVo,"非法参数");
        // 2 校验验证码
        String verifyCode = redisUtils.get(Constants.VERIFY_CODE+":"+loginInfoVo.getUuid());
        Assert.notNull(verifyCode,"验证码不存在");

        Assert.state(VerifyCodeUtil.verification(loginInfoVo.getCode(),verifyCode, true),
                "验证码输入错误");
        // 3 登录
        Employee loginEmployee = employeeMapper.login(loginInfoVo);
        // 4 校验登录逻辑
        Assert.notNull(loginEmployee,"姓名或密码输入错误");
        // 5 登录成功后，存储登录用户，存储相应的权限
        //将登录对象存储到session域对象
        ObjectMapper mapper=new ObjectMapper();
        String loginEmployeeJson = mapper.writeValueAsString(loginEmployee);
        redisUtils.set(Constants.LOGIN_EMPLOYEE+":"+loginEmployee.getId(),loginEmployeeJson);
        //查询登录员工的权限
        List<String> expression = permissionMapper.selectExpressionByEmpId(loginEmployee.getId());
        String expressionJson=mapper.writeValueAsString(expression);
        //将权限存储到session
        redisUtils.set(Constants.EXPRESSION+":"+loginEmployee.getId(),expressionJson);
        return loginEmployee;
}

    @Override
    public Map<String,String> verifyCode() {
        Map<String, String> map = VerifyCodeUtil.generateVerifyCode();
        String uuid=map.get("uuid");
        String code=map.get("code");
        //存储到redis中
        redisUtils.set(Constants.VERIFY_CODE+":"+uuid,code,Constants.CODE_EXPIRES);
        //不需要返回具体验证码
        map.remove("code");
        return map;
    }

    @Override
    public void logout(String userId) {
        // 清除redis登录数据
        String loginEmployeeJson = redisUtils.get(Constants.LOGIN_EMPLOYEE + ":" + userId);
        Assert.notNull(loginEmployeeJson,"非法参数");
        redisUtils.del(Constants.LOGIN_EMPLOYEE+":"+userId);
        redisUtils.del(Constants.EXPRESSION+":"+userId);
    }
}
