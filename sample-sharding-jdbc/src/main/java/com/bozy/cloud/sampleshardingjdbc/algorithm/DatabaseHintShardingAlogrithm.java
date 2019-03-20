package com.bozy.cloud.sampleshardingjdbc.algorithm;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description:
 * Created by tym on 2019/03/19 13:12.
 */
public class DatabaseHintShardingAlogrithm implements HintShardingAlgorithm {

    /**
     * Sharding.
     *
     * <p>sharding value injected by hint, not in SQL.</p>
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        System.out.println("shardingValue=" + JSON.toJSONString(shardingValue));
        System.out.println("availableTargetNames=" + JSON.toJSONString(availableTargetNames));
        List<String> shardingResult = new ArrayList<>();
        for (String targetName : availableTargetNames) {
            String suffix = targetName.substring(targetName.length()-1);
            if(StringUtils.isNumber(suffix)) {
                // hint分片算法的ShardingValue有两种具体类型: ListShardingValue和RangeShardingValue，取决于io.shardingjdbc.core.api.HintManager.addDatabaseShardingValue(String, String, ShardingOperator, Comparable<?>...)的时候,ShardingOperator的类型。见下文。
                ListShardingValue<Integer> tmpSharding = (ListShardingValue<Integer>) shardingValue;
                for(Integer value : tmpSharding.getValues()){
                    if (value % 2 == Integer.parseInt(suffix)) {
                        shardingResult.add(targetName);
                    }
                }
            }
        }

//        availableTargetNames.forEach(targetName -> {
//            String suffix = targetName.substring(targetName.lastIndexOf("_")+1);
//            if(StringUtils.isNumber(suffix)) {
//                // hint分片算法的ShardingValue有两种具体类型: ListShardingValue和RangeShardingValue，取决于io.shardingjdbc.core.api.HintManager.addDatabaseShardingValue(String, String, ShardingOperator, Comparable<?>...)的时候,ShardingOperator的类型。见下文。
//                ListShardingValue<Integer> tmpSharding = (ListShardingValue<Integer>) shardingValue;
//                tmpSharding.getValues().forEach(value -> {
//                    if (value % 2 == Integer.parseInt(suffix)) {
//                        shardingResult.add(targetName);
//                    }
//                });
//            }
//        });
        System.out.println("======DatabaseHintSharding:" + JSON.toJSONString(shardingResult));
        return shardingResult;
    }

}
