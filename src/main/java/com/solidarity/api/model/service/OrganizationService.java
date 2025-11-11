package com.solidarity.api.model.service;

import com.solidarity.api.dto.response.CoordinatesResponse;
import com.solidarity.api.dto.request.OrganizationUpdateRequest;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.model.entity.Organization;
import com.solidarity.api.model.entity.User;
import com.solidarity.api.model.repository.OrganizationDAO;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.exception.SolidarityException;
import com.solidarity.api.mapper.OrganizationMapper;
import com.solidarity.api.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private static final String ORGANIZATION_COVER_PHOTO_PATH = "organization/cover-photo";
    private static final String ORGANIZATION_PROFILE_PHOTO_PATH = "organization/profile-photo";

    private final OrganizationDAO organizationDAO;
    private final OrganizationMapper organizationMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;
    private final GeocodingService geocodingService;
    private final AdministratorService administratorService;

    public OrganizationService(OrganizationDAO organizationDAO,
                               OrganizationMapper organizationMapper,
                               UserService userService,
                               UserMapper userMapper,
                               FileStorageService fileStorageService,
                               GeocodingService geocodingService,
                               AdministratorService administratorService) {
        this.organizationDAO = organizationDAO;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.fileStorageService = fileStorageService;
        this.geocodingService = geocodingService;
        this.administratorService = administratorService;
    }

    public List<OrganizationResponse> findAll() {
        return organizationMapper.toListOrganizationResponse(organizationDAO.findAll());
    }

    @Transactional
    public OrganizationResponse save(OrganizationRequest organizationRequest) {
        try {
            verifyExistsByCnpjAndPhone(organizationRequest.getCnpj(), organizationRequest.getPhone());

            User user = userMapper.toUser(organizationRequest);
            userService.save(user, RolesStatus.ROLE_ORGANIZATION);

            Organization organization = organizationMapper.toOrganization(organizationRequest);
            uploadOrganizationPhotos(organizationRequest, organization);

            CoordinatesResponse coordinates = geocodingService.getCoordinates(organizationRequest.getAddress());
            organization.setLatitude(coordinates.latitude());
            organization.setLongitude(coordinates.longitude());

            organization.setUser(user);
            administratorService.addAdministratorsToOrganization(organizationRequest.getAdministrators(), organization);

            return organizationMapper.toOrganizationResponse(organizationDAO.save(organization));
        } catch (IOException | InterruptedException exception) {
            throw new SolidarityException("Unexpected technical error: " + exception);
        }
    }

    @Transactional
    public void updateOrganization(UUID userId, OrganizationUpdateRequest organizationUpdateRequest) {
        Organization organization = organizationDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("Organization not found."));

        organizationMapper.toUpdateOrganization(organizationUpdateRequest, organization);

        organizationDAO.save(organization);
    }

    @Transactional
    public void deleteOrganization(UUID userId) {
        Organization organization = organizationDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("Organization not found."));

        fileStorageService.deleteFile(organization.getProfilePhoto());
        fileStorageService.deleteFile(organization.getCoverPhoto());

        organizationDAO.delete(organization);
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

    private void uploadOrganizationPhotos (OrganizationRequest organizationRequest, Organization organization) {
        fileStorageService.uploadFile(organizationRequest.getProfilePhoto(), ORGANIZATION_PROFILE_PHOTO_PATH, organization::setProfilePhoto);
        fileStorageService.uploadFile(organizationRequest.getCoverPhoto(), ORGANIZATION_COVER_PHOTO_PATH, organization::setCoverPhoto);
    }
}