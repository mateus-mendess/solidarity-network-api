package com.solidarity.api.domain.service;

import com.solidarity.api.domain.entity.Roles;
import com.solidarity.api.domain.entity.User;
import com.solidarity.api.domain.repository.RolesDAO;
import com.solidarity.api.domain.repository.UserDAO;
import com.solidarity.api.enums.RolesStatus;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.exception.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RolesDAO rolesDAO;

    public UserService(UserDAO userDAO,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       RolesDAO rolesDAO) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.rolesDAO = rolesDAO;
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found."));
    }

    @Transactional
    public void save(User user, RolesStatus rolesStatus) {
        verifyIfEmailAlreadyExists(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Roles roles = rolesDAO.findByName(rolesStatus.toString()).orElseThrow(() -> new NotFoundException("Role not found"));
        user.getRoles().add(roles);
        userDAO.save(user);
    }

    private void verifyIfEmailAlreadyExists(String email) {
        if (userDAO.findByEmail(email).isPresent()) {
            throw new BusinessException("Email already registered");
        }
    }

    private void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("password and password confirmation must be the same");
        }
    }
}
