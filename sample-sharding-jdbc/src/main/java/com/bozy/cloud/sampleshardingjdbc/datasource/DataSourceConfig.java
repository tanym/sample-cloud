package com.bozy.cloud.sampleshardingjdbc.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2019/03/11 15:44.*/


@Configuration
@MapperScan(basePackages = "com.bozy.cloud.sampleshardingjdbc.dao.mapper", sqlSessionTemplateRef  = "sqlSessionTemplate")
public class DataSourceConfig {

    /**mapper文件所在目录**/
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
    private static final String CONFIG_LOCATION = "classpath:mybatis/mybatis-config.xml";
    /**针对所有的Service层接口做数据源的切面切入**/
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.bozy.cloud.sampleshardingjdbc.service..*.*(..))";

    /**
     * Description: 配置事务管理器
     * @Author tym
     * @Create Date: 2019/3/15/0015 下午 2:41
     * @param dataSource
     * @return
     */
    @Bean("transactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        bean.setConfigLocation(new DefaultResourceLoader().getResource(CONFIG_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
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

}
