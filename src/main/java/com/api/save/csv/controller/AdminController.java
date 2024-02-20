package com.api.save.csv.controller;

import com.api.save.csv.entity.Admin;
import com.api.save.csv.entity.dto.request.AdminRequestPostDTO;
import com.api.save.csv.entity.dto.request.AdminRequestPutDTO;
import com.api.save.csv.entity.dto.response.AdminResponseDTO;
import com.api.save.csv.exception.AdminNotFoundException;
import com.api.save.csv.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Find all Admin", tags = "Admin")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AdminResponseDTO>> findAll() {
        List<Admin> adminList = adminService.findAll();
        return new ResponseEntity<>(AdminResponseDTO.fromAdminList(adminList), HttpStatus.OK);
    }

    @Operation(summary = "Find Users by Admin", tags = "Admin")
    @GetMapping(path = "/name/{admin_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AdminResponseDTO>> findByName(@PathVariable(name = "admin_name") String name) {
        try {
            List<Admin> adminList = adminService.findByName(name);
            return new ResponseEntity<>(AdminResponseDTO.fromAdminList(adminList), HttpStatus.OK);
        } catch (AdminNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Find Admin by ID", tags = "Admin")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{admin_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AdminResponseDTO> findById(@PathVariable(name = "admin_id") Long id) {
        try {
            Admin admin = adminService.findById(id);
            return new ResponseEntity<>(new AdminResponseDTO(admin), HttpStatus.OK);
        } catch (AdminNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Update a Admin", tags = "Admin")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@RequestBody AdminRequestPutDTO adminRequestPutDTO) {
        try {
            adminService.update(adminRequestPutDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a Admin", tags = "Admin")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{admin_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "admin_id") Long id) {
        adminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/register")
    @Operation(summary = "register new Admin", tags = "Admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody @Valid AdminRequestPostDTO adminRequest) {
        if (adminService.findEmailLogin(adminRequest.email()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            adminService.save(adminRequest);
            return ResponseEntity.ok().build();
        }
    }
}
