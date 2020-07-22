package com.java.apps.listen01helloword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ＠SpringBootApplication加在项目的启动类上，实际上是一个组合注解：
 * 　＠SpringBootConfiguration 表明这是一个配置类，可以在这个类中配置Bean。
 * 　＠EnableAutoConfiguration 表示开启自动化配置，其实是一个＠Configuration注解。
 * 　　Spring Boot中的自动化配置是非侵入式的，在任意时刻，都可以使用自定义配置代替自动化配置中的某一个配置。
 * 　＠ComponentScan 完成包扫描，默认扫描的类都位于当前类所在包的下面。
 * 　　此注解会扫描＠Service, @Repository、＠Component、@Controller、＠RestController、＠Configuration注解的类。
 * 　＠Filter 过滤器
 */
@SpringBootApplication
public class Listen01HellowordApplication {

    public static void main(String[] args) {
        SpringApplication.run(Listen01HellowordApplication.class, args);
    }

}
