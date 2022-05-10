package com.example.corespringsecurity.dto;

import com.example.corespringsecurity.domain.Role;
import com.example.corespringsecurity.domain.RoleResources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesDto{

    private String id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String roleName;
    private Set<RoleResources> roleResources;

}
