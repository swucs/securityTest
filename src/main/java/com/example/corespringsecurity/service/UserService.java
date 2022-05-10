package com.example.corespringsecurity.service;

import com.example.corespringsecurity.domain.Account;
import com.example.corespringsecurity.dto.AccountDto;

import java.util.List;

public interface UserService {
    void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);
}
