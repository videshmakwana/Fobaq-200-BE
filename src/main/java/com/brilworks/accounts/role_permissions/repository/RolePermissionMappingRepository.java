package com.brilworks.accounts.role_permissions.repository;

import com.brilworks.accounts.role_permissions.entity.RolePermissionMapping;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMappingRepository extends JpaRepository<RolePermissionMapping, Long> {
    @Query(value = "select r from RolePermissionMapping r where r.roleId=:roleId")
    List<RolePermissionMapping> findAllByRoleId(@Param("roleId") Long roleId);

    @Query(value = "SELECT rolePermission.permission.permissionKey FROM RolePermissionMapping rolePermission WHERE rolePermission.roleId IN (:roleIds)")
    List<PermissionsEnum> findAllPermissions(@Param("roleIds") List<Long> roleIds);

    @Modifying
    @Query(value = "delete from RolePermissionMapping r where r.roleId=:roleId and r.permissionId=:perId")
    void deleteByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("perId") Long perId);

    @Modifying
    @Query("delete from RolePermissionMapping r where r.roleId=:roleId")
    void deleteAllRolePermissionMappingByRoleId(@Param("roleId") Long roleId);
}
