package com.ftn.upp.service.impl;

import com.ftn.upp.model.Authority;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findAuthorityByName(String name) {
        return this.authorityRepository.findAuthorityByName(name);
    }
}
