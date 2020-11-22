package com.tonglxer.spring.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * 一个自定义注解的测试
 *  * 这里没有定义特别的操作只是标记为一个注解
 *  * 在CommonResponse中对该注解进行判定后可以实现特定功能
 *  * 此处实现的功能如注解名所示
 *
 *
 * @Author Tong LinXing
 * @date 2020/11/22
 */
@Target({ElementType.TYPE, ElementType.METHOD})//可以标识在类或方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface IgnoreResponseAdvice {
}
