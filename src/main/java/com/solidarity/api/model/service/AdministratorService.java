package com.solidarity.api.model.service;

import com.solidarity.api.model.entity.Administrator;
import com.solidarity.api.model.entity.Organization;
import com.solidarity.api.model.repository.AdministratorDAO;
import com.solidarity.api.dto.request.AdministratorRequest;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.mapper.AdministratorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdministratorService {

    private final AdministratorDAO administratorDAO;
    private final AdministratorMapper administratorMapper;

    public AdministratorService(AdministratorDAO administratorDAO, AdministratorMapper administratorMapper) {
        this.administratorDAO = administratorDAO;
        this.administratorMapper = administratorMapper;
    }

    public void addAdministratorsToOrganization(List<AdministratorRequest> administratorRequests, Organization organization) {
        for (AdministratorRequest administratorRequest : administratorRequests) {
            Administrator administrator = administratorMapper.toAdministrator(administratorRequest);

            administrator.setOrganization(organization);
            organization.getAdministrators().add(administrator);
        }
    }

    public Administrator findById(UUID id) {
        return administratorDAO.findById(id).orElseThrow(() -> new NotFoundException("Administrator not found!"));
    }
}
