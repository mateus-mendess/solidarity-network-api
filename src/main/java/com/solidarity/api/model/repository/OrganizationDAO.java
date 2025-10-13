package com.solidarity.api.model.repository;

import com.solidarity.api.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationDAO extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByCnpjOrPhone(String cnpj, String phone);
}
