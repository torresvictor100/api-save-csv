package com.api.save.csv.service;

import com.api.save.csv.entity.Admin;
import com.api.save.csv.entity.dto.request.AdminRequestPostDTO;
import com.api.save.csv.entity.dto.request.AdminRequestPutDTO;

import java.util.List;

public interface AdminService {

    List<Admin> findAll();

    Admin save(AdminRequestPostDTO adminRequest);

    Admin findById(Long id);

    List<Admin> findByName(String name);

    Admin findByEmail(String email);

    Admin findEmailLogin(String email);

    Admin update(AdminRequestPutDTO adminRequest);

    void deleteById(Long id);
}
