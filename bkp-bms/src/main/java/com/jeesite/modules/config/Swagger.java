package com.jeesite.modules.config;

import com.jeesite.modules.swagger.config.SwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ConditionalOnProperty(
        name = {"web.swagger.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class Swagger {
    public Swagger(){
    }
    @Bean
    public Docket bkpApi() {
        String moduleCode = "bkp";
        String moduleName = "博坎普模块";
        String basePackage = "com.jeesite.modules.bkp.web";
        return SwaggerConfig.docket(moduleCode, moduleName, basePackage).select().apis(RequestHandlerSelectors.basePackage(basePackage)).build();
    }
}
