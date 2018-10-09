package com.bozy.cloud.annotation;

import com.bozy.cloud.enums.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 数据源切换的注解
 * Created by tym on 2018/08/28 16:02.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface TargetDataSource {
//    String value();
    DataSourceKey dataSourceKey() default DataSourceKey.DB_MASTER;
}
