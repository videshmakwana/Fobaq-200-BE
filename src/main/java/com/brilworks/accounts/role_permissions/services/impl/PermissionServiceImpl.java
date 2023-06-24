package com.brilworks.accounts.role_permissions.services.impl;

import com.brilworks.accounts.exception.NotAcceptableException;
import com.brilworks.accounts.role_permissions.dto.PermissionDto;
import com.brilworks.accounts.role_permissions.entity.Permission;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import com.brilworks.accounts.role_permissions.repository.PermissionRepository;
import com.brilworks.accounts.role_permissions.services.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission findById(long permissionId) {
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
        if (permissionOpt.isPresent()) {
            return permissionOpt.get();
        }
        return null;
    }


    @Override
    @Transactional
    public Long createPermission(PermissionDto dto) {
//        checkIsPermissionNameUnique(dto);
        return this.permissionRepository.save(new PermissionDto().dtoToEntity(dto)).getId();

    }

    private void checkIsPermissionNameUnique(PermissionDto dto) {
        if (!StringUtils.isBlank(dto.getName())) {
            Permission permission = permissionRepository.findByPermissionKey(dto.getPermissionKey().name());
            if (null != permission) {
                throw new NotAcceptableException(NotAcceptableException.NotAceptableExeceptionMSG.ROLE_NAME_NOT_EMPTY);
            }
        }
    }

    @Override
    @Transactional
    public Long updatePermission(PermissionDto dto) {
        Permission permission = permissionRepository.findByPermissionKey(dto.getPermissionKey().name());
        if (null == permission) {
            // throw expetion role not found
            return null;
        }
        permission.setName(dto.getName());
//        permission.setDescription(dto.getDescription());
        permission.setPermissionKey(dto.getPermissionKey());
//        permission.setPermissionType(dto.getPermissionType());
//        permission.setUpdatedAt(new Date());
        permissionRepository.save(permission);
        return permission.getId();
    }

    @Override
    @Transactional
    public List<PermissionDto> getPermissions() {
        List<PermissionDto> permissionList = new ArrayList<>();
        for (Permission permission : permissionRepository.findAll()) {
            permissionList.add(new PermissionDto().entityToDto(permission));
        }
        return permissionList;
    }

    @Override
    public Permission getPermissionByPermissionKey(String permissionKey) {
        Permission permission = permissionRepository.findByPermissionKey(PermissionsEnum.valueOf(permissionKey));

        if (null == permission) {
            return null;
        }
        return permission;
    }

    @Override
    @Transactional
    public void deletePermission(long permissionId) {
        permissionRepository.deletePermissionById(permissionId);
    }

    @Override
    public Long findByModuleTypeAndPermissionKey(ModuleType moduleType, PermissionsEnum permissionsEnum) {
        return permissionRepository.findByModuleTypeAndPermissionKey(moduleType,permissionsEnum);
    }

    @Override
    public List<Permission> findByModuleType(ModuleType moduleType) {
        return permissionRepository.findByModuleType(moduleType);
    }
}
