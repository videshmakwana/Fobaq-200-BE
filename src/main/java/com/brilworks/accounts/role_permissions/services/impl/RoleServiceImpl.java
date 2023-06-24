package com.brilworks.accounts.role_permissions.services.impl;


import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.ConflictException;
import com.brilworks.accounts.exception.NotAcceptableException;
import com.brilworks.accounts.exception.NotFoundException;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.enums.DefaultRole;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import com.brilworks.accounts.role_permissions.repository.RoleRepository;
import com.brilworks.accounts.role_permissions.services.RolePermissionMappingService;
import com.brilworks.accounts.role_permissions.services.RoleService;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.brilworks.accounts.role_permissions.enums.DefaultRole.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;
    @Autowired
    private UserRoleService userRoleService;
//    @Autowired
//    private RolePermissionMappingRepository rolePermissionMappingRepository;

    @Override
    public Role getRoleDetailsByIdAndOrgId(Long roleId, Long orgId) {
        return roleRepository.findByIdAndOrgId(roleId, orgId);
    }

    @Override
    public List<Role> findByRoleIdsInOrganizationAndModule(Long orgId, ModuleType moduleType) {
        return roleRepository.findByOrganisationIdAndModuleType(orgId, moduleType);
    }

    @Override
    @Transactional
    public Long createRole(RoleDto dto, Long orgId) {
        checkIsRoleNameUnique(dto.getName(), orgId);
        Role role = dto.dtoToEntity(dto);
        role.setOrganisationId(orgId);
        role = roleRepository.save(role);
        return role.getId();
    }

    private void checkIsRoleNameUnique(String roleName, Long orgId) {
        if (!StringUtils.isBlank(roleName)) {
            Role role = roleRepository.findByNameAndOrgId(roleName, orgId);
            if (null != role) {
                throw new ConflictException(ConflictException.UserExceptionConflictMsg.ROLE_ALREADY_PRESENT);
            }
        } else {
            throw new NotAcceptableException(NotAcceptableException.NotAceptableExeceptionMSG.ROLE_NAME_NOT_EMPTY);
        }

    }

    @Override
    @Transactional
    public Long updateRole(RoleDto dto) {
        Role role = getRoleDetailsByIdAndOrgId(dto.getRoleId(), dto.getOrganisationId());
        if (null == role || !role.getModuleType().equals(dto.getModuleType())) {
            throw new NotFoundException(NotFoundException.NotFound.ROLE_NOT_FOUND);
        }

        role = roleRepository.save(new RoleDto().dtoToEntity(dto));
        return role.getId();
    }

    @Override
    @Transactional
    public List<RoleDto> getRoles(Long orgId, String moduleType) {
        ModuleType moduleType1 = ModuleType.valueOf(moduleType);
        return roleRepository.getAllRoles(orgId, moduleType1);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty()) {
            throw new NotFoundException(NotFoundException.NotFound.ROLE_NOT_FOUND);
        }
        List<UserRole> list = userRoleService.findByRoleId(roleId);
        if (!CollectionUtils.isEmpty(list)) {
            throw new NotFoundException(NotFoundException.NotFound.ROLE_ALREADY_SAVE_WITH_OTHER_USER);
        }
        rolePermissionMappingService.deleteRolePermissionsOfRoleByRoleId(roleId);
        roleRepository.delete(role.get());
    }

    public RoleDto findRoleDtoById(Long roleId, Long orgId) {
        return new RoleDto().entityToDto(getRoleDetailsByIdAndOrgId(roleId, orgId));
    }

    //    public Role handleSignUpRole(Long orgId){
//        Role role = prepareDefaultRole(ModuleType.ALL, ROLE_ADMIN_ALL);
//        role.setOrganisationId(orgId);
//        role = roleRepository.save(role);
//        rolePermissionMappingService.createDefaultPermissionBrilCRM(role,ModuleType.EMP);
//        return role;
//    }
    @Override
    public void handleDefaultRoleCreation(Organisation organisation, ModuleType moduleType, User user) {
        Role role = null;
        switch (moduleType) {
            case EMP:
                role = createDefaultBrilCRMRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role.getId(), organisation.getId());
                break;
            case OKR:
                role = createDefaultOKRRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role.getId(), organisation.getId());
                break;
            case TASK:
                role = createDefaultTaskMRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role.getId(), organisation.getId());
                break;
            default:
                Role role1 = createDefaultBrilCRMRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role1.getId(), organisation.getId());
                Role role2 = createDefaultOKRRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role2.getId(), organisation.getId());
                Role role3 = createDefaultTaskMRole(organisation.getId());
                userRoleService.assignRoleToUser(user.getId(), role3.getId(), organisation.getId());
        }
    }

    @Override
    public List<Permission> getAllPermissionByOrgIdAndUserId(Long organizationId, Long userId) {
        return roleRepository.getAllPermissionByOrgIdAndUserId(organizationId);
    }

    @Override
    public List<UserRole> getUserRoleByUserIdAndOrgId(Long userId, Long orgId) {
        return userRoleService.getUserRoleByUserIdAndOrgId(userId, orgId);
    }

    @Override
    public Role findRoleByRoleId(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        return role.orElse(null);
    }

    @Override
    public Role getRoleByOrgIdAndRoleType(Long orgId, ModuleType moduleType) {
        Role role11 = new Role();
        switch (moduleType) {
            case EMP:
                role11 = roleRepository.getRoleByOrgIdAndRoleType(orgId, "ROLE_USER_EMP");
                break;
            case TASK:
                role11 = roleRepository.getRoleByOrgIdAndRoleType(orgId, "ROLE_USER_TASK");
                break;
            case OKR:
                role11 = roleRepository.getRoleByOrgIdAndRoleType(orgId, "ROLE_USER_OKR");
                break;
        }
        return role11;
    }

    @Override
    public void changeRoleByOldRoleId(Long userId, Long newRoleId, Long oldRoleId) {
        userRoleService.changeRoleByOldRoleId(userId, newRoleId, oldRoleId);
    }


    @Override
    public List<RoleDto> getRolesByEmpIdAndModule(Long userId, String moduleType) {
        ModuleType type = null;
        if (moduleType.equals("TASK")) {
            type = ModuleType.TASK;
        } else if (moduleType.equals("OKR")) {
            type = ModuleType.OKR;
        } else if (moduleType.equals("EMP")) {
            type = ModuleType.EMP;
        } else {
            type = ModuleType.ALL;
        }
        return roleRepository.getRolesByEmpIdAndModule(userId, type);
    }

    public Role createDefaultBrilCRMRole(Long orgId) {
        Role role = prepareDefaultRole(ModuleType.EMP, ROLE_ADMIN_EMP, orgId);
        rolePermissionMappingService.createDefaultPermission(role, PermissionsEnum.ALL_EMP);
        Role role1 = prepareDefaultRole(ModuleType.EMP, ROLE_USER_EMP, orgId);
        rolePermissionMappingService.createDefaultPermission(role1, PermissionsEnum.BASE_EMP_PERMISSION);
        return role;
    }

    public Role createDefaultOKRRole(Long orgId) {
        Role role = prepareDefaultRole(ModuleType.OKR, ROLE_ADMIN_OKR, orgId);
        rolePermissionMappingService.createDefaultPermission(role, PermissionsEnum.ALL_OKR);

        Role role1 = prepareDefaultRole(ModuleType.OKR, ROLE_USER_OKR, orgId);
        rolePermissionMappingService.createDefaultPermission(role1, PermissionsEnum.BASE_OKR_PERMISSION);

        return role;
    }

    public Role createDefaultTaskMRole(Long orgId) {
        Role role = prepareDefaultRole(ModuleType.TASK, ROLE_ADMIN_TASK, orgId);
        rolePermissionMappingService.createDefaultPermission(role, PermissionsEnum.ALL_TASK);

        Role role1 = prepareDefaultRole(ModuleType.TASK, ROLE_USER_TASK, orgId);
        rolePermissionMappingService.createDefaultPermission(role1, PermissionsEnum.BASE_TASK_PERMISSION);

        return role;
    }

    private Role prepareDefaultRole(ModuleType moduleType, DefaultRole defaultRole, Long orgId) {
        Role role = new Role();
        role.setIsDefault(true);
        role.setModuleType(moduleType);
        role.setName(defaultRole.name());
        role.setDescription(defaultRole.getDescription());
        role.setDisplayName(defaultRole.getDisplayName());
        role.setOrganisationId(orgId);

        roleRepository.save(role);
        return role;
    }
}
