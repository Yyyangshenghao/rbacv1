package cn.wolfcode.rbac.web.advice;

import cn.wolfcode.rbac.domain.vo.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception exception){
        exception.printStackTrace();
        return R.error(exception.getMessage());
    }
}
