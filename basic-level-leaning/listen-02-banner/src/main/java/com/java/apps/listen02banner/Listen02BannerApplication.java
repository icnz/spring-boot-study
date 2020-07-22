package com.java.apps.listen02banner;

import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;

/**
 * 在resources目录下创建一个banner.txt 文件，在这个文件中写入的文本将在项目启动时打印出来。
 * 如果banner指定为其他名称，需要在application.properties中指定：spring.banner.location=favorite.txt
 * 或在程序中定制。
 */
@SpringBootApplication
public class Listen02BannerApplication {

    public static void main(String[] args) {
        // 定制banner
        // SpringApplication springApplication = new SpringApplication(Listen02BannerApplication.class);
        // springApplication.setBanner(new ResourceBanner(new ClassPathResource("favorite.txt")));
        // springApplication.run(args);

        // 关闭banner
        // 使用SpringApplicationBuilder关闭banner
        // SpringApplicationBuilder builder = new SpringApplicationBuilder(Listen02BannerApplication.class);
        // builder.bannerMode(Banner.Mode.OFF).run(args);

        // 使用SpringApplication关闭banner
        SpringApplication application = new SpringApplication(Listen02BannerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
