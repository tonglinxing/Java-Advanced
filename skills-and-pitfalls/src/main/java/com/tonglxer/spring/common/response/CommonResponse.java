package com.tonglxer.spring.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 自定义响应实体类
 * 作用：
 * 1. 统一管理响应信息
 * 2. 可以通过自定义响应码和信息快速定位问题
 *
 * @Author Tong LinXing
 * @date 2020/11/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private Integer code;//响应代码
    private String message;//响应信息
    private T data;//泛型信息数据

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}