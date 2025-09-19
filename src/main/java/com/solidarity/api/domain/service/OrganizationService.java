package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.Organization;
import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.repository.OrganizationDAO;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.request.UserRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.mapper.OrganizationMapper;
import com.solidarity.api.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    private final OrganizationDAO organizationDAO;
    private final OrganizationMapper organizationMapper;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public OrganizationService(OrganizationDAO organizationDAO,
                               OrganizationMapper organizationMapper,
                               UserService userService,
                               BCryptPasswordEncoder bCryptPasswordEncoder,
                               UserMapper userMapper) {
        this.organizationDAO = organizationDAO;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public OrganizationResponse save(OrganizationRequest organizationRequest) {
        verifyExistsByCnpjAndPhone(organizationRequest.getCnpj(), organizationRequest.getPhone());
        User user = userMapper.toUser(organizationRequest);
        userService.save(user, RolesStatus.ROLE_ORGANIZATION);
        Organization organization = organizationMapper.toOrganization(organizationRequest);
        return organizationMapper.toOrganizationResponse(organizationDAO.save(organization));
    }

    private void verifyExistsByCnpjAndPhone(String cnpj, String phone) {
        Optional<Organization> organization = organizationDAO.findByCnpjOrPhone(cnpj, phone);
        if(organization.isPresent()) {

            if (organization.get().getCnpj().equals(cnpj)) {
                throw new BusinessException("There is already an NGO with this CNPJ");
            } else if (organization.get().getCnpj().equals(phone)) {
                throw new BusinessException("There is already an NGO with this phone");
            }

        }
    }
}
