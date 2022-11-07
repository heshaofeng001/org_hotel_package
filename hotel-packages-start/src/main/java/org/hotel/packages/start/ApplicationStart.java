package org.hotel.packages.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@ImportResource({
        "classpath*:META-INF/spring/spring-aop.xml"
})
@SpringBootApplication(scanBasePackages = {"org.hotel.packages"})
@MapperScan(basePackages={"org.hotel.packages.dal"})
@EnableDubbo(scanBasePackages = {"org.hotel.packages"})
@EnableRedisWebSession
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}