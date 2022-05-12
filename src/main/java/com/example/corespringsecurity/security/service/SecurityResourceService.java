package com.example.corespringsecurity.security.service;

import com.example.corespringsecurity.domain.Resources;
import com.example.corespringsecurity.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourcesRepository) {
        this.resourcesRepository = resourcesRepository;
    }

    /**
     * DB에 저장된 권한 정보를 Security에서 원하는 형태(LinkedHashMap<RequestMatcher, List<ConfigAttribute>>)로 반환한다.
     * @return
     */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();

        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleResources().forEach(rr -> {
                configAttributeList.add(new SecurityConfig(rr.getRole().getRoleName()));
            });
            result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
        });

        return result;
    }

    /**
     * DB에 저장된 권한 정보를 Security에서 원하는 형태(LinkedHashMap<String, List<ConfigAttribute>>)로 반환한다.
     * @return
     */
    public LinkedHashMap<String, List<ConfigAttribute>> getMethodResourceList() {
        LinkedHashMap<String, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllMethodResources();

        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleResources().forEach(rr -> {
                configAttributeList.add(new SecurityConfig(rr.getRole().getRoleName()));
            });
            result.put(re.getResourceName(), configAttributeList);
        });

        return result;
    }

    /**
     * DB에 저장된 권한 정보를 Security에서 원하는 형태(LinkedHashMap<String, List<ConfigAttribute>>)로 반환한다.
     * @return
     */
    public LinkedHashMap<String, List<ConfigAttribute>> getPointcutResourceList() {
        LinkedHashMap<String, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllPointcutResources();

        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleResources().forEach(rr -> {
                configAttributeList.add(new SecurityConfig(rr.getRole().getRoleName()));
            });
            result.put(re.getResourceName(), configAttributeList);
        });

        return result;
    }
}
