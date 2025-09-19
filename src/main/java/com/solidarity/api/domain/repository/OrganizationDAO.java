package com.solidarity.api.domain.repository;

import com.solidarity.api.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationDAO extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByCnpjOrPhone(String cnpj, String phone);
}
