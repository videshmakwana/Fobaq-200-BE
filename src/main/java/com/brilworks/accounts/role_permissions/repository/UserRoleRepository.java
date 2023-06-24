package com.brilworks.accounts.role_permissions.repository;

import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT  u FROM UserRole u where userId=:userId and roleId=:roleId")
    List<UserRole> findByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Query("SELECT  u FROM UserRole u where roleId=:roleId")
    List<UserRole> findByRoleId(@Param("roleId") Long roleId);

    @Query("select u from UserRole u where u.userId=:userId")
    List<UserRole> getByUserId(@Param("userId") Long userId);

    List<UserRole> findByUserIdAndOrganizationId(Long userId, Long orgId);

    @Query("SELECT  distinct ur.organizationId FROM UserRole ur where userId=:userId")
    List<Long> getOrganisationIdByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM UserRole u where u.organizationId=:orgId and u.userId=:userId")
    List<UserRole> getUserRoleByUserIdAndOrgId(@Param("userId") Long userId, @Param("orgId") Long orgId);

    @Query("SELECT new com.brilworks.accounts.role_permissions.dto.RoleDto(u.role) FROM UserRole u where u.organizationId=:orgId and u.userId=:userId")
    List<RoleDto> getRoleIdByUserIdAndOrgId(@Param("userId") Long userId, @Param("orgId") Long orgId);

    @Query("SELECT ur FROM UserRole ur where ur.roleId=:oldRoleId and ur.userId=:userId")
    UserRole findUserRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("oldRoleId") Long oldRoleId);
}
