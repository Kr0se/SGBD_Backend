package com.online.files.online.files.services;

import com.online.files.online.files.models.ProvaCustomer;
import com.online.files.online.files.repositories.PhotoRepository;
import com.online.files.online.files.repositories.ProvaCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProvaCustomerService {

    @Autowired
    private ProvaCustomerRepository customerRepository;

    public Collection<ProvaCustomer> getCustormers(){
        return customerRepository.findAll();
    }
}
