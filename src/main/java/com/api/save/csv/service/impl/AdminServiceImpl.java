package com.api.save.csv.service.impl;

import com.api.save.csv.entity.Admin;
import com.api.save.csv.entity.UserType;
import com.api.save.csv.entity.dto.request.AdminRequestPostDTO;
import com.api.save.csv.entity.dto.request.AdminRequestPutDTO;
import com.api.save.csv.exception.AdminNotFoundException;
import com.api.save.csv.exception.AdminNotSavedException;
import com.api.save.csv.exception.AdminNotUpdateException;
import com.api.save.csv.repository.AdminRepository;
import com.api.save.csv.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin save(AdminRequestPostDTO adminRequest) {
        Admin admin = adminRepository.save(createAdmin(adminRequest));
        if (admin != null) {
            return admin;
        } else {
            throw new AdminNotSavedException("Admin not saved");
        }
    }

    @Override
    public Admin findById(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        return optionalAdmin.orElseThrow(() -> new AdminNotFoundException("Admin not found"));
    }

    @Override
    public List<Admin> findByName(String name) {
        return adminRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Admin findByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        return optionalAdmin.orElseThrow(() -> new AdminNotFoundException("Admin not found"));
    }

    @Override
    public Admin findEmailLogin(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        return optionalAdmin.orElse(null);
    }

    @Override
    public Admin update(AdminRequestPutDTO adminRequest) {
        Admin admin = findById(adminRequest.id());
        if (adminRequest != null) {

            setName(adminRequest, admin);
            setEmail(adminRequest, admin);
            setPassword(adminRequest, admin);
            setType(adminRequest, admin);

            return adminRepository.save(admin);
        } else {
            throw new AdminNotUpdateException("Admin not update");
        }
    }

    @Override
    public void deleteById(Long id) {
        Admin admin = new Admin();
        admin.setId(id);
        adminRepository.delete(admin);
    }

    private static Admin createAdmin(AdminRequestPostDTO adminRequest) {
        Admin admin = new Admin(null, adminRequest.name(), adminRequest.email()
                , new BCryptPasswordEncoder().encode(adminRequest.password())
                , null, null, UserType.ADMIN);
        return admin;
    }

    private static void setType(AdminRequestPutDTO adminRequest, Admin admin) {
        if (adminRequest.type() != null) {
            admin.setType(adminRequest.type());
            setUpdateDate(admin);
        }
    }

    private static void setPassword(AdminRequestPutDTO adminRequest, Admin admin) {
        if (adminRequest.password() != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(adminRequest.password());
            admin.setPassword(encryptedPassword);
            setUpdateDate(admin);
        }
    }

    private static void setEmail(AdminRequestPutDTO adminRequest, Admin admin) {
        if (adminRequest.email() != null) {
            admin.setEmail(adminRequest.email());
            setUpdateDate(admin);
        }
    }

    private static void setName(AdminRequestPutDTO adminRequest, Admin admin) {
        if (adminRequest.name() != null) {
            admin.setName(adminRequest.name());
            setUpdateDate(admin);
        }
    }

    private static void setUpdateDate(Admin admin) {
        admin.setUpdateDate(new Timestamp(System.currentTimeMillis()));
    }
}
