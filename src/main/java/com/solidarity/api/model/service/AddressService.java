package com.solidarity.api.model.service;

import com.solidarity.api.model.repository.AddressDAO;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressDAO addressDAO;

    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
