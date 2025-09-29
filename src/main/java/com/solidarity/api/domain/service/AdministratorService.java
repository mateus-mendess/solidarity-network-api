package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.Administrator;
import com.solidarity.api.domain.repository.AdministratorDAO;
import com.solidarity.api.dto.request.AdministratorRequest;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.mapper.AdministratorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdministratorService {

    private final AdministratorDAO administratorDAO;
    private final AdministratorMapper administratorMapper;

    public AdministratorService(AdministratorDAO administratorDAO, AdministratorMapper administratorMapper) {
        this.administratorDAO = administratorDAO;
        this.administratorMapper = administratorMapper;
    }

    @Transactional
    public Administrator save(AdministratorRequest administratorRequest) {
        return administratorDAO.save(administratorMapper.toAdministrator(administratorRequest));
    }

    public Administrator findById(UUID id) {
        return administratorDAO.findById(id).orElseThrow(() -> new NotFoundException("Administrator not found!"));
    }
}
