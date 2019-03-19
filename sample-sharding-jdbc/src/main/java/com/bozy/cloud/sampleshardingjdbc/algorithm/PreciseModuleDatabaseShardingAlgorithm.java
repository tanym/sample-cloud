package com.bozy.cloud.sampleshardingjdbc.algorithm;

import com.alibaba.fastjson.JSON;
import com.bozy.cloud.sampleshardingjdbc.common.ShardingSphereConstants;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * Description:
 * Created by tym on 2019/03/12 11:37.
 */
public class PreciseModuleDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println("collection:" + JSON.toJSONString(availableTargetNames) + ",preciseShardingValue:" + JSON.toJSONString(preciseShardingValue));
        for (String name : availableTargetNames) {
            // =与IN中分片键对应的值
            String value = String.valueOf(preciseShardingValue.getValue());
            // 分库的后缀
            int i = 1;
            // 求分库后缀名的递归算法
            if (name.endsWith("_" + countDatabaseNum(Long.parseLong(value), i))) {
                return name;
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 计算该量级的数据在哪个数据库
     * @return
     */
    private String countDatabaseNum(long columnValue, int i){
        // ShardingSphereConstants每个库中定义的数据量
        long left = ShardingSphereConstants.databaseAmount * (i-1);
        long right = ShardingSphereConstants.databaseAmount * i;
        if(left < columnValue && columnValue <= right){
            return String.valueOf(i);
        }else{
            i++;
            return countDatabaseNum(columnValue, i);
        }
    }

}
