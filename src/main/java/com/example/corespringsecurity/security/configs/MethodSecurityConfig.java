package com.example.corespringsecurity.security.configs;

import com.example.corespringsecurity.security.factory.MethodResourcesFactoryBean;
import com.example.corespringsecurity.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true, jsr250Enabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {


    private final SecurityResourceService securityResourceService;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {

        LinkedHashMap<String, List<ConfigAttribute>> object = methodResourcesFactoryBean().getObject();
        System.out.println("object : " + object);

        return new MapBasedMethodSecurityMetadataSource(object);
    }

    @Bean
    public MethodResourcesFactoryBean methodResourcesFactoryBean() {
        MethodResourcesFactoryBean methodResourcesFactoryBean = new MethodResourcesFactoryBean();
        methodResourcesFactoryBean.setSecurityResourceService(securityResourceService);
        methodResourcesFactoryBean.setResourceType("method");
        return methodResourcesFactoryBean;
    }

}
