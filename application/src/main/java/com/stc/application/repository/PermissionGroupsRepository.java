package com.stc.application.repository;


import com.stc.application.model.domain.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionGroupsRepository extends JpaRepository<PermissionGroup, String> {
}
