package com.tonglxer.spring.common.response;

import com.tonglxer.spring.common.annotation.IgnoreResponseAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 拦截控制器返回的响应
 * 包装后返回
 *
 * @Author Tong LinXing
 * @date 2020/11/22
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {


    /**
     * 响应拦截器生效的判断
     *
     * @return true为执行该类的beforeBodyWrite方法，反之则然
     * */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        // 针对类注解， 判断是否被自定义@IgnoreResponseAdvice注解标注
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class)) {
            return false;
        }
        // 针对方法注解
        if (methodParameter.getMethod().isAnnotationPresent(//针对方法处
                IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Nullable//表示对应的值可以为空
    @Override
    @SuppressWarnings("all")//忽略所有警告
    public Object beforeBodyWrite(@Nullable Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        CommonResponse<Object> response = new CommonResponse<>(0, "This is a empty response.");//默认为空响应
        if (null == o) {
            return response;
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
        } else {
            // 将原生响应数据直接存入响应data中
            response.setData(o);
        }
        return response;
    }
}

