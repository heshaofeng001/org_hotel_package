package org.student.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.student.web.msg.consumer.AmqListenerScan;

@ImportResource({
        "classpath*:META-INF/spring/spring-aop.xml"
})
@SpringBootApplication(scanBasePackages = {"org.student"})
@MapperScan(basePackages={"org.student.dal"})
@EnableDubbo(scanBasePackages = {"org.student"})
@AmqListenerScan(basePackages={"org.student"})
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}