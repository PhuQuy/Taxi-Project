package com.spring.config;
 
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
@EnableWebMvc
@Configuration
@ComponentScan({ "com.spring.*" })
@EnableTransactionManagement
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder
        	.scanPackages("com.spring.entity")
            .addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }
	
	private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.enable_lazy_load_no_trans", "true");        
        prop.put("hibernate.connection.characterEncoding", "utf8");
        prop.put("hibernate.connection.CharSet", "utf8");
        prop.put("hibernate.connection.useUnicode", "true");
        //prop.put("hibernate.hbm2ddl.import_files", "data.sql");
        return prop;
    }
	
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
	    ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/track_app?characterEncoding=UTF-8");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
	
	@Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
//		Resource resources=n
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry.addResourceHandler("/css/**")
		    .addResourceLocations("/WEB-INF/css/");
		  registry.addResourceHandler("/images/**")
		    .addResourceLocations("/WEB-INF/images/");
		  registry.addResourceHandler("/js/**")
		    .addResourceLocations("/WEB-INF/js/");
		}
}