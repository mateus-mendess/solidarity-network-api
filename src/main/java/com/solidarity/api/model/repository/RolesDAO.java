package com.solidarity.api.model.repository;

import com.solidarity.api.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesDAO extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName (String name);
}
