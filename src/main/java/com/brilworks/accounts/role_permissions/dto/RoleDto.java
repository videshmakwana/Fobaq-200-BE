package com.brilworks.accounts.role_permissions.dto;

import com.brilworks.accounts.role_permissions.entity.Role;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

    private Long roleId;

    private String name;

    private String description;

    private Date createdAt;

    private boolean isDefault;

    private Long organisationId;

    private ModuleType moduleType;

    public RoleDto(long id, String name, String description, Date createdAt, Long organisationId) {
        this.roleId = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.organisationId = organisationId;
    }

    public Role dtoToEntity(RoleDto roleDto) {
        Role role = new Role();
        if (roleDto.getRoleId() != null) {
            role.setId(roleDto.getRoleId());
        }
        role.setIsDefault(roleDto.isDefault());
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        if (roleDto.getCreatedAt() == null) {
            role.setCreatedAt(new Date());
        }
        role.setCreatedAt(roleDto.getCreatedAt());
        if (roleDto.getRoleId() != null) {
            role.setUpdatedAt(new Date());
        }
        role.setModuleType(roleDto.getModuleType());
        role.setDisplayName(roleDto.getModuleType().name());
        role.setOrganisationId(roleDto.getOrganisationId());
        return role;
    }

    public RoleDto entityToDto(Role role) {
        return setRoleDto(role, new RoleDto());
    }

    private RoleDto setRoleDto(Role role, RoleDto roleDto) {
        roleDto.setRoleId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setOrganisationId(role.getOrganisationId());
        roleDto.setDescription(role.getDescription());
        roleDto.setCreatedAt(role.getCreatedAt());
        roleDto.setModuleType(role.getModuleType());
        roleDto.setDefault(role.getIsDefault());
        return roleDto;
    }

    public RoleDto(Role role) {
        setRoleDto(role, this);
    }

    public static void main(String[] args) {
        new RoleDto(new Role());
    }
}
