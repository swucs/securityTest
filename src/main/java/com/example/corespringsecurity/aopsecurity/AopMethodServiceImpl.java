package com.example.corespringsecurity.aopsecurity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class AopMethodServiceImpl implements AopMethodService {

    @Override
    @Secured("ROLE_MANAGER")
    public void methodSecured() {
        System.out.println("methodSecured");
    }
}
