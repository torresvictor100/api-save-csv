package com.api.save.csv.entity.dto.request;

import com.api.save.csv.entity.UserType;

public record AdminRequestPutDTO(long id, String name, String email, String password, UserType type) {
}
