package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.Roles;
import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.entity.Volunteer;
import com.solidarity.api.domain.repository.RolesDAO;
import com.solidarity.api.domain.repository.VolunteerDAO;
import com.solidarity.api.dto.request.UserRequest;
import com.solidarity.api.dto.request.VolunteerRequest;
import com.solidarity.api.dto.response.VolunteerResponse;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.mapper.UserMapper;
import com.solidarity.api.mapper.VolunteerMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VolunteerService {

    private final UserService userService;
    private final VolunteerDAO volunteerDAO;
    private final VolunteerMapper volunteerMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final RolesDAO rolesDAO;
    private final FileStorageService fileStorageService;

    public VolunteerService(
            UserService userService,
            VolunteerDAO volunteerDAO,
            VolunteerMapper volunteerMapper,
            UserMapper userMapper,
            BCryptPasswordEncoder cryptPasswordEncoder,
            RolesDAO rolesDAO,
            FileStorageService fileStorageService) {
        this.userService = userService;
        this.volunteerDAO = volunteerDAO;
        this.volunteerMapper = volunteerMapper;
        this.userMapper = userMapper;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.rolesDAO = rolesDAO;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public VolunteerResponse save(VolunteerRequest volunteerRequest) {
        verifyIfCpfAlreadyExists(volunteerRequest.getCpf());
        User user = userMapper.toUser(volunteerRequest);
        userService.save(user, RolesStatus.ROLE_VOLUNTEER);
        Volunteer volunteer = volunteerMapper.toVolunteer(volunteerRequest);
        volunteer.setProfilePhoto(fileStorageService.uploadFile(volunteerRequest.getProfilePhoto()));
        volunteer.setUser(user);
        return volunteerMapper.toVolunteerResponse(volunteerDAO.save(volunteer));
    }

    private void verifyIfCpfAlreadyExists(String cpf) {
        if (volunteerDAO.existsByCpf(cpf)) {
            throw new BusinessException("CPF already registered");
        }
    }
}
