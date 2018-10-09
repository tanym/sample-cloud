package com.bozy.cloud.annotation;

import java.lang.annotation.*;

/**
 * Description: 读库
 * Created by tym on 2018/08/27 14:01.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReadDataSource {

}
