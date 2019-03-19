package com.bozy.cloud.sampleshardingjdbc.datasource;

import com.bozy.cloud.sampleshardingjdbc.algorithm.DatabaseShardingAlgorithm;
import com.bozy.cloud.sampleshardingjdbc.algorithm.TablePreciseShardingAlgorithm;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.shardingsphere.api.algorithm.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.exception.ShardingException;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.spring.boot.util.PropertyUtil;
import io.shardingsphere.shardingjdbc.util.DataSourceUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * Description: 分库分表数据源
 * Created by tym on 2019/03/15 10:12.
 */
//@Configuration
//@MapperScan(basePackages = ShardingDataSourceConfig.BASE_PACKAGES, sqlSessionTemplateRef = "sqlSessionTemplate")
public class ShardingDataSourceConfig {

    @Autowired
    Environment env;

    /**DAO层Mapper接口所在目录**/
    static final String BASE_PACKAGES = "com.bozy.cloud.sampleshardingjdbc.dao.mapper";

    /**mapper文件所在目录**/
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    private static final String CONFIG_LOCATION = "classpath:mybatis/mybatis-config.xml";

    /**针对所有的Service层接口做数据源的切面切入**/
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.bozy.cloud.sampleshardingjdbc.service..*.*(..))";
    /**
     * Description: 创建分库数据源
     * @Author tym
     * @Create Date: 2019/3/15/0015 上午 10:37
     * @return
     * @throws SQLException
     */
    @Bean
    public DataSource shardingDataSource() throws SQLException {
           ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
           shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
           shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
           shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
    //          shardingRuleConfig.getBroadcastTables().add("t_config");
//           shardingRuleConfig.setDefaultDataSourceName();
           shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", new DatabaseShardingAlgorithm()));
           shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new TablePreciseShardingAlgorithm()));
           shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
           return ShardingDataSourceFactory.createDataSource(createDataSourceMap(env), shardingRuleConfig, new HashMap<String, Object>(), new Properties());
    }

    /**
     * Description: 订单分表规则
     * @Author tym
     * @Create Date: 2019/3/15/0015 上午 10:25
     * @return
     */
    public TableRuleConfiguration getOrderTableRuleConfiguration() {
           TableRuleConfiguration result = new TableRuleConfiguration();
           result.setLogicTable("t_order");
           result.setActualDataNodes("ds_${0..1}.t_order_${[0, 1]}");
           result.setKeyGeneratorColumnName("order_id");
           return result;
    }

    /**
     * Description: 订单明细分表规则
     * @Author tym
     * @Create Date: 2019/3/15/0015 上午 10:26
     * @return
     */
    public TableRuleConfiguration getOrderItemTableRuleConfiguration() {
           TableRuleConfiguration result = new TableRuleConfiguration();
           result.setLogicTable("t_order_item");
           result.setActualDataNodes("ds_${0..1}.t_order_item_${[0, 1]}");
           return result;
    }

    /**
     * Description: 主从读写分离规则
     * @Author tym
     * @Create Date: 2019/3/15/0015 上午 10:26
     * @return
     */
    public List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
           //MasterSlaveRuleConfiguration:(名称,主数据源,从数据源,负载均衡算法-轮询或随机)
           MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds_0", "demo_ds_master_0", Arrays.asList("demo_ds_master_0_slave_0", "demo_ds_master_0_slave_1"), new RoundRobinMasterSlaveLoadBalanceAlgorithm());
           MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration("ds_1", "demo_ds_master_1", Arrays.asList("demo_ds_master_1_slave_0", "demo_ds_master_1_slave_1"), new RoundRobinMasterSlaveLoadBalanceAlgorithm());
           return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }

    /**
     * Description: 多数据源Map
     * @Author tym
     * @Create Date: 2019/3/15/0015 上午 10:27
     * @return
     */
    private Map<String, DataSource> createDataSourceMap(final Environment environment) {
        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
        String prefix = "sharding.jdbc.datasource.";
        String dataSources = environment.getProperty(prefix + "names");
        for (String each : dataSources.split(",")) {
            try {
                Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + each, Map.class);
                Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
                DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
                dataSourceMap.put(each, dataSource);
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException("Can't find datasource type!", ex);
            }
        }
        return dataSourceMap;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //此处设置为了解决找不到mapper文件的问题
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        /**mybatis config配置文件路径**/
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(CONFIG_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     *
     * @return 事务管理实例
     */
    @Bean("transactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*事务拦截类型*/
    @Bean("txSource")
    public TransactionAttributeSource transactionAttributeSource(){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        //RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //requiredTx.setRollbackRules(
        //    Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("*", readOnlyTx);
        source.setNameMap( txMap );

        return source;
    }

    /**切面拦截规则 参数会自动从容器中注入*/
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor txInterceptor){
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setAdvice(txInterceptor);
        pointcutAdvisor.setExpression(AOP_POINTCUT_EXPRESSION);
        return pointcutAdvisor;
    }

    /*事务拦截器*/
    @Bean("txInterceptor")
    TransactionInterceptor getTransactionInterceptor(@Qualifier("transactionManager") PlatformTransactionManager transactionManager){
        return new TransactionInterceptor(transactionManager , transactionAttributeSource()) ;
    }

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        /**是否将参数offset作为PageNum使用**/
        properties.setProperty("offsetAsPageNum","true");
        /**是否进行count查询**/
//        properties.setProperty("rowBoundsWithCount","true");
        /**是否分页合理化; 默认值为false。为true时,pageNum<=0 时会查询第一页,pageNum>pages（超过总数时）,会查询最后一页;默认false 时,直接根据参数进行查询**/
        properties.setProperty("reasonable","true");
//        properties.setProperty("params", "count=countSql");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
