package com.solidarity.api.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidarity.api.domain.entity.Administrator;
import com.solidarity.api.domain.entity.Organization;
import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.repository.OrganizationDAO;
import com.solidarity.api.dto.request.AdministratorRequest;
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
    private final ObjectMapper objectMapper;
    private final AdministratorService administratorService;

    public OrganizationService(OrganizationDAO organizationDAO,
                               OrganizationMapper organizationMapper,
                               UserService userService,
                               UserMapper userMapper,
                               FileStorageService fileStorageService,
                               GeocodingService geocodingService,
                               ObjectMapper objectMapper,
                               AdministratorService administratorService) {
        this.organizationDAO = organizationDAO;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.fileStorageService = fileStorageService;
        this.geocodingService = geocodingService;
        this.objectMapper = objectMapper;
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
            handleUploadPhoto(organizationRequest, organization);
            String coordinates = geocodingService.getCoordinates(
                    organizationRequest.getAddress().getPostalCode(),
                    organizationRequest.getAddress().getNeighborhood(),
                    organizationRequest.getAddress().getStreet(),
                    organizationRequest.getAddress().getCity(),
                    organizationRequest.getAddress().getState()
            );
            JsonNode root = objectMapper.readTree(coordinates);
            organization.setLatitude(root.get(0).get("lat").asDouble());
            organization.setLongitude(root.get(0).get("lon").asDouble());
            for (AdministratorRequest administratorRequest : organizationRequest.getAdministrators()) {
                Administrator administrator = administratorService.save(administratorRequest);
                organization.getAdministrators().add(administrator);
            }
            organization.setUser(user);
            return organizationMapper.toOrganizationResponse(organizationDAO.save(organization));
        } catch (IOException | InterruptedException exception) {
            throw new SolidarityException("Unexpected technical error: " + exception);
        }
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
