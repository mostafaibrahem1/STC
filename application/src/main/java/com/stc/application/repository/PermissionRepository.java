package com.stc.application.repository;


import com.stc.application.model.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, String> {
    Permission findByUserEmail(String email);
}
