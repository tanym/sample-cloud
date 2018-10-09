package com.bozy.cloud.datasource.dynamicDataSourceConfig;

import com.bozy.cloud.enums.DataSourceKey;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * Description:
 * Created by tym on 2018/09/03 14:00.
 */
public class DynamicDataSourceContextHolder {
    private static final Logger LOG = Logger.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceKey> currentDatesource = new ThreadLocal<>();

    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDatesource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSourceKey get() {
        return currentDatesource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSourceKey value) {
        currentDatesource.set(value);
    }

    /**
     * 设置从从库读取数据
     * 采用简单生成随机数的方式切换不同的从库
     */
    public static void setSlave() {
//        if (RandomUtils.nextInt(0, 2) > 0) {
//            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE2);
//        } else {
//            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE1);
//        }
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE);
    }
}
