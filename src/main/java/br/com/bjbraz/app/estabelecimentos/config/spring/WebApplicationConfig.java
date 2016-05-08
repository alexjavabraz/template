package br.com.bjbraz.app.estabelecimentos.config.spring;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("br.com.bjbraz.app.estabelecimentos.web.jsf")
public class WebApplicationConfig {

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("view", new ViewScope());

        CustomScopeConfigurer bean = new CustomScopeConfigurer();
        bean.setScopes(scopes);

        return bean;
    }

}
