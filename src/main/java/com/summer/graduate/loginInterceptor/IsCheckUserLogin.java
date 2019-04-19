package com.summer.graduate.loginInterceptor;

import java.lang.annotation.*;

/**
 * @Author: summer
 * @Date: 2018 18-4-1 下午12:28
 * @Project: LoginInterceptor
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsCheckUserLogin {
	boolean check() default false;
}