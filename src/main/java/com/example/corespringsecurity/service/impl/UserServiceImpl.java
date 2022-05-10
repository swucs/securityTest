package com.example.corespringsecurity.service.impl;

import com.example.corespringsecurity.domain.Account;
import com.example.corespringsecurity.domain.AccountRoles;
import com.example.corespringsecurity.domain.Role;
import com.example.corespringsecurity.dto.AccountDto;
import com.example.corespringsecurity.repository.AccountRolesRepository;
import com.example.corespringsecurity.repository.RoleRepository;
import com.example.corespringsecurity.repository.UserRepository;
import com.example.corespringsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRolesRepository accountRolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUser(Account account){

        Role role = roleRepository.findByRoleName("ROLE_USER");
        Set<AccountRoles> accountRoles = new HashSet<>();
        accountRoles.add(new AccountRoles(account, role));

        account.setAccountRoles(accountRoles);
        userRepository.save(account);
    }

    @Transactional
    @Override
    public void modifyUser(AccountDto accountDto){

        Account account = userRepository.findById(accountDto.getId()).orElseThrow();
        account.setAge(accountDto.getAge());
        account.setUsername(accountDto.getUsername());
        account.setEmail(accountDto.getEmail());
        account.clearAccountRoles();

        if(accountDto.getRoles() != null){
            accountDto.getRoles().forEach(role -> {
                Role r = roleRepository.findByRoleName(role);
                AccountRoles ar = accountRolesRepository.save(new AccountRoles(account, r));
                account.addAccountRoles(ar);
            });
        }
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        System.out.println("account : " + account);
        System.out.println("account : " + account.getAccountRoles());

        userRepository.save(account);

    }

    @Transactional
    public AccountDto getUser(Long id) {

        Account account = userRepository.findById(id).orElse(new Account());
        ModelMapper modelMapper = new ModelMapper();
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        List<String> roles = account.getAccountRoles()
                .stream()
                .map(accountRole -> accountRole.getRole().getRoleName())
                .collect(Collectors.toList());

        accountDto.setRoles(roles);
        return accountDto;
    }

    @Transactional
    public List<Account> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}