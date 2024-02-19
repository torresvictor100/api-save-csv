package com.api.save.csv.entity.dto.response;

import com.api.save.csv.entity.Admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public record AdminResponseDTO(long id, String name, String email, Timestamp createDate, Timestamp updateDate) {

    public AdminResponseDTO(Admin admin) {
        this(admin.getId(), admin.getName(), admin.getEmail(), admin.getCreationDate(), admin.getUpdateDate());
    }

    public static List<AdminResponseDTO> fromAdminList(List<Admin> adminList) {
        List<AdminResponseDTO> dtoList = new ArrayList<>();
        for (Admin admin : adminList) {
            dtoList.add(new AdminResponseDTO(admin));
        }
        return dtoList;
    }

}
