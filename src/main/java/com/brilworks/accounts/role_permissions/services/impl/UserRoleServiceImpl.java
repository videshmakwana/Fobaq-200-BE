package com.brilworks.accounts.role_permissions.services.impl;

import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.repository.UserRoleRepository;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public void assignRoleToUser(Long userId, Long roleId, Long orgId) {
        if (getUserRole(userId, roleId).isEmpty()) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setOrganizationId(orgId);
            userRoleRepository.save(userRole);
        }
    }

    public void deleteUserRole(Long userId, Long roleId) {
        getUserRole(userId, roleId);
    }

    @Override
    public List<UserRole> getUserRole(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<Long> getUserRoleIds(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId).stream().map(e -> e.getRoleId()).collect(Collectors.toList());
    }

    @Override
    public List<Long> getUserRoldIdsInOrg(Long userId, Long orgId) {
        return userRoleRepository.findByUserIdAndOrganizationId(userId, orgId).stream().map(e -> e.getRoleId()).collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleRepository.getByUserId(userId);
    }

    @Override
    public List<Long> getOrganisationIdByUserId(Long id) {
        return userRoleRepository.getOrganisationIdByUserId(id);
    }

    @Override
    public List<UserRole> getUserRoleByUserIdAndOrgId(Long userId, Long orgId) {
        return userRoleRepository.getUserRoleByUserIdAndOrgId(userId, orgId);
    }

    @Override
    public List<RoleDto> getRoleIdByUserIdAndOrgId(Long userId, Long orgId) {
        return userRoleRepository.getRoleIdByUserIdAndOrgId(userId, orgId);
    }

    @Override
    public List<UserRole> findByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<UserRole> findByRoleId(Long roleId) {
        return userRoleRepository.findByRoleId(roleId);
    }

    @Override
    public void changeRoleByOldRoleId(Long userId, Long newRoleId, Long oldRoleId) {
        UserRole userRole = userRoleRepository.findUserRoleByUserIdAndRoleId(userId, oldRoleId);
        userRole.setRoleId(newRoleId);
        userRoleRepository.save(userRole);
    }
}
