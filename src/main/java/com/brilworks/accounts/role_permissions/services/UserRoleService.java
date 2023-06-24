package com.brilworks.accounts.role_permissions.services;

import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleService {
    @Transactional
    void assignRoleToUser(Long userId, Long roleId, Long orgId);

    List<UserRole> getUserRole(Long userId, Long roleId);

    List<Long> getUserRoleIds(Long userId, Long roleId);

    List<Long> getUserRoldIdsInOrg(Long userId, Long orgId);

    List<UserRole> getUserRoleByUserId(Long userId);

    List<Long> getOrganisationIdByUserId(Long id);

    List<UserRole> getUserRoleByUserIdAndOrgId(Long userId, Long orgId);

	List<RoleDto> getRoleIdByUserIdAndOrgId(Long userId, Long orgId);

    List<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    List<UserRole> findByRoleId(Long roleId);

    void changeRoleByOldRoleId(Long userId, Long newRoleId, Long oldRoleId);
}
