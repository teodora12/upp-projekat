package com.ftn.upp.service;

import com.ftn.upp.model.Authority;
import org.springframework.stereotype.Service;

@Service
public interface AuthorityService {

    Authority findAuthorityByName(String name);
}
