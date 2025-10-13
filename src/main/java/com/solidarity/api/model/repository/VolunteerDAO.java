package com.solidarity.api.model.repository;

import com.solidarity.api.model.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VolunteerDAO extends JpaRepository<Volunteer, UUID> {
    Boolean existsByCpf(String cpf);
}
