package com.brilworks.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseAuditInfo implements Serializable {

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt = new Date();
}
