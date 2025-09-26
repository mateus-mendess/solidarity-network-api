package com.solidarity.api.domain.service;

import com.solidarity.api.domain.repository.AddressDAO;
import com.solidarity.api.dto.request.AddressRequest;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.AddressResponse;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressDAO addressDAO;

    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
