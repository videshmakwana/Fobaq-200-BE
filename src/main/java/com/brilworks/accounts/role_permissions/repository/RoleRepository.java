package com.brilworks.accounts.role_permissions.repository;

import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT role FROM Role role WHERE role.id=:id and role.organisationId=:orgId ")
    Role findByIdAndOrgId(@Param("id") Long id, @Param("orgId") Long orgId);

    @Query("SELECT role FROM Role role WHERE role.name=:name and role.organisationId=:orgId ")
    Role findByNameAndOrgId(@Param("name") String name, @Param("orgId") Long id);

    @Query(" SELECT new com.brilworks.accounts.role_permissions.dto.RoleDto(role.id,role.name,role.description,role.createdAt,role.organisationId) " +
            " FROM Role role " +
            " where role.organisationId=:orgId " +
            " AND role.moduleType=:moduleType")
    List<RoleDto> getAllRoles(@Param("orgId") Long orgId, @Param("moduleType") ModuleType moduleType);

    @Modifying
    @Query("DELETE FROM Role role WHERE role.id=:id")
    void deleteRoleById(@Param("id") Long id);

    List<Role> findByOrganisationIdAndModuleType(Long orgId, ModuleType moduleType);

    @Query("SELECT p FROM Permission p\n" +
            "inner join RolePermissionMapping rp\n" +
            "on p.id=rp.permissionId\n" +
            "inner join Role r\n" +
            "on r.id=rp.roleId\n" +
            "inner join UserRole ur\n" +
            "on ur.roleId=r.id\n" +
            "where ur.organizationId=:orgId")
    List<Permission> getAllPermissionByOrgIdAndUserId(@Param("orgId") Long organizationId);

    @Query(" SELECT new com.brilworks.accounts.role_permissions.dto.RoleDto(role) " +
            " FROM Role role " +
            " inner join UserRole ur on role.id = ur.roleId " +
            " where ur.userId=:userId AND role.moduleType=:moduleType")
    List<RoleDto> getRolesByEmpIdAndModule(@Param("userId")Long userId,@Param("moduleType") ModuleType moduleType);

    @Query(value = "select r from Role r where r.organisationId=:orgId and r.name=:roleName")
    Role getRoleByOrgIdAndRoleType(@Param("orgId") Long orgId, @Param("roleName") String roleName);
}
