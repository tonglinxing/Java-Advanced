package com.tonglxer.spring.common.exception;

import com.tonglxer.spring.common.response.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一拦截控制器层面的异常
 * 并实现自定义处理
 *
 * @Author Tong LinXing
 * @date 2020/11/22
 */
@RestControllerAdvice
public class GlobalException {

    //统一处理方法抛出的异常,此处限定了异常类型为自定义异常
    @ExceptionHandler(value = TonglxerException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req,
                                                     TonglxerException ex) {
        CommonResponse<String> response = new CommonResponse<>(520,
                "Please contact TongLinXing");
        response.setData(ex.getMessage());
        return response;
    }
}
