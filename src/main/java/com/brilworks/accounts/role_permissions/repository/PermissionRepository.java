package com.brilworks.accounts.role_permissions.repository;

import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findById(Long id);

    void deletePermissionById(Long permissionId);

    List<Permission> findAll();

    Permission findByPermissionKey(String key);

    Permission findByPermissionKey(PermissionsEnum permissionsEnum);

    @Query("SELECT id FROM Permission WHERE moduleType=:moduleType AND permissionKey=:permissionKey")
    Long findByModuleTypeAndPermissionKey(@Param("moduleType") ModuleType moduleType, @Param("permissionKey") PermissionsEnum permissionsEnum);

    @Query("SELECT p FROM Permission p WHERE moduleType=:moduleType")
    List<Permission> findByModuleType(@Param("moduleType") ModuleType moduleType);
}
