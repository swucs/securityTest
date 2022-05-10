package com.example.corespringsecurity.repository;

import com.example.corespringsecurity.domain.RoleResources;
import com.example.corespringsecurity.domain.RoleResourcesID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourcesRepository extends JpaRepository<RoleResources, RoleResourcesID> {
}
