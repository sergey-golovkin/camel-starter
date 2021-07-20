package arina.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"file:config/camel-context.xml", "file:config/beans/*.xml", "file:config/datasources/*.xml", "file:config/endpoints/*.xml"})
@Configuration
public class Starter
{
    public static void main(String[] args)
    {
        SpringApplication.run(Starter.class, args);
    }
}
