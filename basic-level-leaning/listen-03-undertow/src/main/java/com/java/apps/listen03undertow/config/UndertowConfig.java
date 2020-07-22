package com.java.apps.listen03undertow.config;


import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
public class UndertowConfig {
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
     * 通过UndertowServletWebServerFactory工厂类生成UndertowWebServer实例，修改Undertow相关配置
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory();
        undertowFactory.addBuilderCustomizers((Undertow.Builder builder) -> {
            builder.addHttpListener(httpPort, "0.0.0.0");
            // 开启HTTP2
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
            // 开启Server Push（需要servlet4以上）
            //.setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH,true)
            ;
        });
        undertowFactory.addDeploymentInfoCustomizers(customizers -> {
            // 开启HTTP自动跳转至HTTPS
            customizers.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> httpsPort);
        });
        return undertowFactory;
    }
}
