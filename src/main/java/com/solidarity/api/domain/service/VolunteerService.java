package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.entity.Volunteer;
import com.solidarity.api.domain.repository.VolunteerDAO;
import com.solidarity.api.dto.request.VolunteerRequest;
import com.solidarity.api.dto.response.VolunteerResponse;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.exception.SolidarityException;
import com.solidarity.api.mapper.UserMapper;
import com.solidarity.api.mapper.VolunteerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VolunteerService {

    private static final String VOLUNTEER_PROFILE_PHOTO_PATH = "volunteer/profile-photo";

    private final UserService userService;
    private final VolunteerDAO volunteerDAO;
    private final VolunteerMapper volunteerMapper;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;

    public VolunteerService(
            UserService userService,
            VolunteerDAO volunteerDAO,
            VolunteerMapper volunteerMapper,
            UserMapper userMapper,
            FileStorageService fileStorageService) {
        this.userService = userService;
        this.volunteerDAO = volunteerDAO;
        this.volunteerMapper = volunteerMapper;
        this.userMapper = userMapper;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public VolunteerResponse save(VolunteerRequest volunteerRequest) {
        try {
            verifyIfCpfAlreadyExists(volunteerRequest.getCpf());
            User user = userMapper.toUser(volunteerRequest);
            userService.save(user, RolesStatus.ROLE_VOLUNTEER);
            Volunteer volunteer = volunteerMapper.toVolunteer(volunteerRequest);
            handleUploadPhoto(volunteerRequest, volunteer);
            volunteer.setUser(user);
            return volunteerMapper.toVolunteerResponse(volunteerDAO.save(volunteer));
        } catch (BusinessException exception) {
            throw exception;
        } catch (SolidarityException exception) {
            throw new SolidarityException("Unexpected technical error: " + exception.getMessage());
        }
    }

    private void verifyIfCpfAlreadyExists(String cpf) {
        if (volunteerDAO.existsByCpf(cpf)) {
            throw new BusinessException("CPF already registered");
        }
    }

    private void handleUploadPhoto(VolunteerRequest volunteerRequest, Volunteer volunteer) {
        if (volunteerRequest.getProfilePhoto() != null && !volunteerRequest.getProfilePhoto().isEmpty()) {
            volunteer.setProfilePhoto(fileStorageService.uploadFile(volunteerRequest.getProfilePhoto(), VOLUNTEER_PROFILE_PHOTO_PATH));
        }
    }
}
