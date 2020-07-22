package com.java.apps.listen03jetty.config;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * 实现Jetty重定向
 */
@Configuration
@ConditionalOnProperty(prefix = "custom.server.http", name = "port", matchIfMissing = false)
public class JettyConfig extends AbstractConfiguration implements WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> {

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
     * 实现Jetty重定向
     *
     * @param context
     * @throws Exception
     */
    @Override
    public void configure(WebAppContext context) throws Exception {
        Constraint constraint = new Constraint();
        constraint.setDataConstraint(Constraint.DC_CONFIDENTIAL);

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        ConstraintSecurityHandler handler = new ConstraintSecurityHandler();
        handler.addConstraintMapping(mapping);

        context.setSecurityHandler(handler);
    }

    /**
     * 同时打开http和https
     * WebServerFactoryCustomizerConfig的功能主要是在有https的前提下，还要提供http
     *
     * @param factory
     */
    @Override
    public void customize(ConfigurableJettyWebServerFactory factory) {
        // ((JettyServletWebServerFactory) factory).setConfigurations(
        //         Collections.singleton(new JettyConfig())
        // );
        factory.addServerCustomizers(
                server -> {
                    HttpConfiguration httpConfiguration = new HttpConfiguration();
                    httpConfiguration.setSecurePort(httpsPort);
                    httpConfiguration.setSecureScheme("https");

                    ServerConnector connector = new ServerConnector(server);
                    connector.addConnectionFactory(new HttpConnectionFactory(httpConfiguration));
                    connector.setPort(httpPort);
                    // 可以实现多个http端口同时启用，并都重定向到https
                    //ServerConnector connector = new ServerConnector(server);
                    //connector.addConnectionFactory(new HttpConnectionFactory(httpConfiguration));
                    //connector.setPort(httpPort);

                    server.addConnector(connector);
                }
        );
    }
}
