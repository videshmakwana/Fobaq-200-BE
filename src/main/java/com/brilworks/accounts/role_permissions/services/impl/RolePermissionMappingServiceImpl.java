package com.brilworks.accounts.role_permissions.services.impl;


import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.NotFoundException;
import com.brilworks.accounts.role_permissions.dto.PermissionDto;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.dto.RoleWithListOfPermissionDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.entity.RolePermissionMapping;
import com.brilworks.accounts.role_permissions.entity.UserRole;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import com.brilworks.accounts.role_permissions.repository.RolePermissionMappingRepository;
import com.brilworks.accounts.role_permissions.repository.UserRoleRepository;
import com.brilworks.accounts.role_permissions.services.*;
import com.brilworks.accounts.role_permissions.services.PermissionService;
import com.brilworks.accounts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionMappingServiceImpl implements RolePermissionMappingService {
    @Autowired
    private RolePermissionMappingRepository rolePermissionMappingRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public void saveListOfPermissionWithRoleId(RoleWithListOfPermissionDto dto, Long orgId) {
        if (dto == null) {
            throw new NotFoundException(NotFoundException.NotFound.BAD_REQUEST);
        }
        RoleDto roleDto = dto.getRole();
        roleDto.setOrganisationId(orgId);
        Long roleId = roleService.createRole(roleDto, orgId);

        if (!CollectionUtils.isEmpty(dto.getPermissionList()) && roleId != null) {
            for (PermissionDto permissionDto : dto.getPermissionList()) {
                RolePermissionMapping rolePermissionMapping = new RolePermissionMapping();
                rolePermissionMapping.setRoleId(roleId);
                rolePermissionMapping.setPermissionId(permissionDto.getId());
                rolePermissionMappingRepository.save(rolePermissionMapping);
            }
        }
    }

    @Override
    public List<String> getPermissionListInArray(Long userId, Long organizationId, String moduleType) {
        User user = userService.getUserById(userId);
        ModuleType moduleType1 = ModuleType.valueOf(moduleType);
        List<Long> userRolesIds = userRoleService.getUserRoldIdsInOrg(user.getId(), organizationId);
        List<Long> orgRoleIds = roleService.findByRoleIdsInOrganizationAndModule(organizationId, moduleType1).stream().map(Role::getId).collect(Collectors.toList());
        List<Long> userRoleInOrgAndModule = userRolesIds.stream().filter(orgRoleIds::contains).collect(Collectors.toList());

        if (!userRoleInOrgAndModule.isEmpty()) {
            return rolePermissionMappingRepository.findAllPermissions(userRoleInOrgAndModule).stream().map(Enum::toString).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PermissionsEnum> getPermissionList(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return rolePermissionMappingRepository.findAllPermissions(roleIds);
    }

    @Override
    public RoleWithListOfPermissionDto getRoleAndPermissionListByRoleId(Long roleId, Long orgId) {
        RoleWithListOfPermissionDto roleWithListOfPermissionDto = new RoleWithListOfPermissionDto();
        List<RolePermissionMapping> list = rolePermissionMappingRepository.findAllByRoleId(roleId);
        List<PermissionDto> permissionDtoList = new ArrayList<>();

        RoleDto roleDto = roleService.findRoleDtoById(roleId, orgId);
        if (roleDto != null) {
            roleWithListOfPermissionDto.setRole(roleDto);
        }

        if (!CollectionUtils.isEmpty(list)) {
            for (RolePermissionMapping rolePermissionMapping : list) {
                permissionDtoList.add(new PermissionDto().entityToDto(rolePermissionMapping.getPermission()));
            }
            roleWithListOfPermissionDto.setPermissionList(permissionDtoList);
        }
        return roleWithListOfPermissionDto;
    }

    @Transactional
    @Override
    public void updateRolePermissionMapping(RoleWithListOfPermissionDto dto, Long organizationId) {
        if (dto == null) {
            throw new NotFoundException(NotFoundException.NotFound.BAD_REQUEST);
        }
        Long roleId = null;
        RoleDto roleDto = dto.getRole();
        roleDto.setOrganisationId(organizationId);
        if (roleDto != null) {
            roleId = roleService.updateRole(roleDto);
        }
        List<RolePermissionMapping> oldPermissionByRoleId = rolePermissionMappingRepository.findAllByRoleId(dto.getRole().getRoleId());
        if (!CollectionUtils.isEmpty(oldPermissionByRoleId)) {
            rolePermissionMappingRepository.deleteAll(oldPermissionByRoleId);
        }
        if (!CollectionUtils.isEmpty(dto.getPermissionList()) && roleId != null) {
            for (PermissionDto permissionDto : dto.getPermissionList()) {
                RolePermissionMapping rolePermissionMapping = new RolePermissionMapping();
                rolePermissionMapping.setRoleId(roleId);
                rolePermissionMapping.setPermissionId(permissionDto.getId());
                rolePermissionMappingRepository.save(rolePermissionMapping);
            }
        }
    }

    @Override
    @Transactional

    public void deleteRolePermissionsOfRole(Long roleId) {
        roleService.deleteRole(roleId);
    }


    @Override
    public void mapAdminRoleWithPermission(Long roleId) {
        Permission permission = permissionService.getPermissionByPermissionKey("ALL");
        if (null != permission) {
            RolePermissionMapping rolePermissionMapping = new RolePermissionMapping();
            rolePermissionMapping.setRoleId(roleId);
            rolePermissionMapping.setPermissionId(permission.getId());
            rolePermissionMappingRepository.save(rolePermissionMapping);
        }
    }

    @Override
    public void createDefaultPermission(Role role, PermissionsEnum permissionsEnum) {
        Long perId = permissionService.findByModuleTypeAndPermissionKey(role.getModuleType(), permissionsEnum);
        RolePermissionMapping rolePermissionMapping = new RolePermissionMapping();
        rolePermissionMapping.setPermissionId(perId);
        rolePermissionMapping.setRoleId(role.getId());
        rolePermissionMappingRepository.save(rolePermissionMapping);
    }

    @Override
    public List<Permission> getAllPermissionsOfModule(Long organizationId, ModuleType moduleType) {
        return permissionService.findByModuleType(moduleType);
    }

    @Override
    public List<RoleDto> getRoleIdByUserIdAndOrgId(Long userId, Long orgId) {
        return userRoleService.getRoleIdByUserIdAndOrgId(userId, orgId);
    }

    @Override
    public void deleteRolePermissionsOfRoleByRoleId(Long roleId) {
        rolePermissionMappingRepository.deleteAllRolePermissionMappingByRoleId(roleId);
    }

}