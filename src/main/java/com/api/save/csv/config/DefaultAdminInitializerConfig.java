package com.api.save.csv.config;

import com.api.save.csv.entity.Admin;
import com.api.save.csv.entity.UserType;
import com.api.save.csv.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminInitializerConfig implements CommandLineRunner {

    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.email}")
    private String defaultAdminEmail;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    private final AdminRepository adminRepository;

    public DefaultAdminInitializerConfig(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findByEmail(defaultAdminEmail) == null) {
            Admin admin = new Admin();
            admin.setId(null);
            admin.setName(defaultAdminName);
            admin.setEmail(defaultAdminEmail);
            admin.setPassword(new BCryptPasswordEncoder().encode(defaultAdminPassword));
            admin.setType(UserType.ADMIN);
            adminRepository.save(admin);
        }
    }
}
