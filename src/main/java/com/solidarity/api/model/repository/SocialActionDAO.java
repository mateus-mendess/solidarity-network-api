package com.solidarity.api.model.repository;

import com.solidarity.api.model.entity.SocialAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SocialActionDAO extends JpaRepository<SocialAction, UUID> {
}
