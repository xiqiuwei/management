package com.management.shiro.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author xiqiuwei
 * @Date Created in 22:28 2019/12/4
 * @Description
 * @Modified By:
 */
@SuppressWarnings("Duplicates")
@Configuration
public class DruidConfig {
    @Value("${druid.spring.datasource.url}")
    private String url;

    @Value("${druid.spring.datasource.username}")
    private String username;

    @Value("${druid.spring.datasource.password}")
    private String password;

    @Value("${druid.spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${druid.spring.datasource.initial-size}")
    private int initialSize;

    @Value("${druid.spring.datasource.min-idle}")
    private int minIdle;

    @Value("${druid.spring.datasource.max-active}")
    private int maxActive;

    @Value("${druid.spring.datasource.max-wait}")
    private int maxWait;

    @Value("${druid.spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${druid.spring.datasource.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;

    @Value("${druid.spring.datasource.validation-query}")
    private String validationQuery;

    @Value("${druid.spring.datasource.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${druid.spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${druid.spring.datasource.test-on-return}")
    private boolean testOnReturn;

    @Value("${druid.spring.datasource.filters}")
    private String filters;

    @Value("{druid.spring.datasource.connection-properties}")
    private String connectionProperties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);   //这里可以做加密处理
        datasource.setDriverClassName(driverClassName);
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        datasource.setRemoveAbandoned(true);
        datasource.setRemoveAbandonedTimeout(300);
        datasource.setLogAbandoned(false);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        datasource.setConnectionProperties(connectionProperties);
        return datasource;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//        servletRegistrationBean.addInitParameter("allow","127.0.0.1");  //设置ip白名单
//        servletRegistrationBean.addInitParameter("deny","192.168.0.19");//设置ip黑名单，优先级高于白名单
        //设置控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername","root");
//        servletRegistrationBean.addInitParameter("loginPassword","root");
        //是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean statFilter(){
        //创建过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
