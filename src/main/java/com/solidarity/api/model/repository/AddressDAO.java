package com.solidarity.api.model.repository;


import com.solidarity.api.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDAO extends JpaRepository<Address, Long> {
}
