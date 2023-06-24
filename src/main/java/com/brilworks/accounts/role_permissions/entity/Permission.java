package com.brilworks.accounts.role_permissions.entity;

import com.brilworks.accounts.role_permissions.enums.ModuleType;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;
import lombok.*;

import javax.persistence.*;

@Table(name = "permissions")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "permission_key", unique = true)
    @Enumerated(EnumType.STRING)
    private PermissionsEnum permissionKey;

    @Column(name = "module_type")
    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;

}