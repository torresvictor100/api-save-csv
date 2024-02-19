package com.api.save.csv.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_sq")
    @SequenceGenerator(name = "admin_sq", sequenceName = "admin_sq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp creationDate;
    @CreationTimestamp
    private Timestamp updateDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;
}
