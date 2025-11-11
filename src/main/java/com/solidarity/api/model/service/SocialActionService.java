package com.solidarity.api.model.service;

import com.solidarity.api.dto.request.SocialActionRequest;
import com.solidarity.api.dto.response.CoordinatesResponse;
import com.solidarity.api.dto.response.SocialActionCardResponse;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.exception.SolidarityException;
import com.solidarity.api.mapper.SocialActionMapper;
import com.solidarity.api.model.entity.Category;
import com.solidarity.api.model.entity.Organization;
import com.solidarity.api.model.entity.SocialAction;
import com.solidarity.api.model.repository.CategoryDAO;
import com.solidarity.api.model.repository.OrganizationDAO;
import com.solidarity.api.model.repository.SocialActionDAO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SocialActionService {
    private static final String SOCIAL_ACTION_IMAGE_PATH = "social-action/image";

    private final SocialActionDAO socialActionDAO;
    private final SocialActionMapper socialActionMapper;
    private final OrganizationDAO organizationDAO;
    private final GeocodingService geocodingService;
    private final CategoryDAO categoryDAO;
    private final FileStorageService fileStorageService;

    public SocialActionService(SocialActionDAO socialActionDAO, SocialActionMapper socialActionMapper, OrganizationDAO organizationDAO, GeocodingService geocodingService, CategoryDAO categoryDAO, FileStorageService fileStorageService) {
        this.socialActionDAO = socialActionDAO;
        this.socialActionMapper = socialActionMapper;
        this.organizationDAO = organizationDAO;
        this.geocodingService = geocodingService;
        this.categoryDAO = categoryDAO;
        this.fileStorageService = fileStorageService;
    }

    public SocialActionCardResponse save(SocialActionRequest socialActionRequest, MultipartFile image, UUID organizationId) {

        try {
            Organization organization = organizationDAO.findById(organizationId)
                    .orElseThrow(() -> new NotFoundException("organization not found."));

            Set<Category> categories = categoryDAO.findAllById(socialActionRequest.getCategoryIds()).stream().collect(Collectors.toSet());

            CoordinatesResponse coordinatesResponse = geocodingService.getCoordinates(socialActionRequest.getAddress());

            SocialAction socialAction = socialActionMapper.toSocialAction(socialActionRequest);
            socialAction.setOrganization(organization);
            socialAction.setLatitude(coordinatesResponse.latitude());
            socialAction.setLongitude(coordinatesResponse.longitude());
            socialAction.setCategories(categories);

            fileStorageService.uploadFile(image, SOCIAL_ACTION_IMAGE_PATH, socialAction::setImage);

            return socialActionMapper.toSocialActionResponse(socialActionDAO.save(socialAction));
        } catch (IOException | InterruptedException exception) {
            throw new SolidarityException(exception.getMessage());
        }
    }

}
