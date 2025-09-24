package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.Organization;
import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.repository.OrganizationDAO;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.mapper.OrganizationMapper;
import com.solidarity.api.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrganizationService {
    private static final String ORGANIZATION_COVER_PHOTO_PATH = "organization/cover-photo";
    private static final String ORGANIZATION_PROFILE_PHOTO_PATH = "organization/profile-photo";

    private final OrganizationDAO organizationDAO;
    private final OrganizationMapper organizationMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;

    public OrganizationService(OrganizationDAO organizationDAO,
                               OrganizationMapper organizationMapper,
                               UserService userService,
                               UserMapper userMapper,
                               FileStorageService fileStorageService) {
        this.organizationDAO = organizationDAO;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public OrganizationResponse save(OrganizationRequest organizationRequest) {
        verifyExistsByCnpjAndPhone(organizationRequest.getCnpj(), organizationRequest.getPhone());
        User user = userMapper.toUser(organizationRequest);
        userService.save(user, RolesStatus.ROLE_ORGANIZATION);
        Organization organization = organizationMapper.toOrganization(organizationRequest);
        handleUploadPhoto(organizationRequest, organization);
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

    private void handleUploadPhoto (OrganizationRequest organizationRequest, Organization organization) {
        if (organizationRequest.getProfilePhoto() != null && !organizationRequest.getProfilePhoto().isEmpty()) {
            organization.setProfilePhoto(fileStorageService.uploadFile(organizationRequest.getProfilePhoto(), ORGANIZATION_PROFILE_PHOTO_PATH));
        }

        if (organizationRequest.getCoverPhoto() != null && !organizationRequest.getCoverPhoto().isEmpty()) {
            organization.setCoverPhoto(fileStorageService.uploadFile(organizationRequest.getCoverPhoto(), ORGANIZATION_COVER_PHOTO_PATH));
        }
    }
}
