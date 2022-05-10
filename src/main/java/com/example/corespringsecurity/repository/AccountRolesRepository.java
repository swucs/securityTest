package com.example.corespringsecurity.repository;

import com.example.corespringsecurity.domain.AccountRoles;
import com.example.corespringsecurity.domain.AccountRolesID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRolesRepository extends JpaRepository<AccountRoles, AccountRolesID> {
}
