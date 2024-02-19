package com.api.save.csv.repository;

import com.api.save.csv.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByNameContainingIgnoreCase(String name);

    Optional<Admin> findByEmail(String fullName);
}
