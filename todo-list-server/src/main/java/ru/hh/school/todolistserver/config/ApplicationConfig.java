package ru.hh.school.todolistserver.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.hh.school.todolistserver.entity.Task;
import ru.hh.school.todolistserver.resource.TaskResource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Value("${task.db.url}")
    private String taskDbUrl;

    @Value("${task.db.user}")
    private String taskDbUser;

    @Value("${task.db.password}")
    private String taskDbPassword;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(taskDbUrl, taskDbUser, taskDbPassword);
    }

    @Bean
    LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, PostgreSQL10Dialect.class.getName());
        properties.put(Environment.HBM2DDL_AUTO, "update");
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("dao");
        localSessionFactoryBean.setAnnotatedClasses(Task.class);
        return localSessionFactoryBean;
    }

    @Bean
    public SessionFactory sessionFactory(LocalSessionFactoryBean localSessionFactoryBean) {
        return localSessionFactoryBean.getObject();
    }

    @Bean
    public HibernateTransactionManager platformTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public ResourceConfig jerseyConfig() {
        ResourceConfig config = new ResourceConfig();
        config.register(TaskResource.class);
        config.property(ServletProperties.FILTER_FORWARD_ON_404, true);
        return config;
    }
}
