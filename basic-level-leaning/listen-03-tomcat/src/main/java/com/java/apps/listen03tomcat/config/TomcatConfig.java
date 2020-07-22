package com.java.apps.listen03tomcat.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监听http端口，将访问网址为http协议的，自动转换为Https
 * Spring Boot1.x是UndertowEmbeddedServletContainerFactory
 * Spring Boot2.x是UndertowServletWebServerFactory
 * <p>
 * ＠ConditionalOnProperty注解，用于控制@Configuration是否生效
 * value：数组，获取对应property名称的值，与name不可同时使用
 * prefix：property名称的前缀，可有可无
 * name：数组，property完整名称或部分名称（可与prefix组合使用，组成完整的property名称），与value不可同时使用
 * havingValue：可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * matchIfMissing：缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之不加载配置
 * relaxedNames：是否可以松散匹配
 */
@Configuration
@ConditionalOnProperty(prefix = "custom.server.http", name = "port", matchIfMissing = false)
public class TomcatConfig {

    /**
     * 读取application.properties配置文件配置的https访问端口号
     */
    @Value("${server.port}")
    private int httpsPort;
    /**
     * 读取application.properties配置文件配置的http监控端口（自动转换为https）
     */
    @Value("${custom.server.http.port}")
    private int httpPort;

    /**
     * 通过TomcatServletWebServerFactory工厂类生成TomcatWebServer实例，修改Tomcat相关配置
     *
     * @return
     */
    @Bean
    TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        factory.addAdditionalTomcatConnectors(createTomcatConnector());
        return factory;
    }

    /**
     * 生成tomcat连接器
     *
     * @return
     */
    private Connector createTomcatConnector() {
        // 创建一个http协议的连接器
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        // 设置协议头
        connector.setScheme("http");
        // 设置监听的端口号
        connector.setPort(httpPort);
        // 设置安全链接标志，false表示可以http重定向至https，true则表示http使用http，而https使用https
        connector.setSecure(false);
        // 监听到http的端口号后重定向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }
}
