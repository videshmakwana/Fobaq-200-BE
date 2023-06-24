package com.brilworks.accounts.role_permissions.entity;

import com.brilworks.accounts.entity.BaseAuditInfo;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.role_permissions.enums.ModuleType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
@Entity
public class Role extends BaseAuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description")
    private String description;


    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "module_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Organisation.class)
    @JoinColumn(name = "organisation_id", updatable = false, insertable = false)
    private Organisation organisation;

    @Column(name = "organisation_id", nullable = false)
    private Long organisationId;
}

