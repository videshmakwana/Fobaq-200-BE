package com.brilworks.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization")
@Entity
public class Organisation extends BaseAuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "organisation_url", unique = true)
    private String organisationUrl;

    @Column(name = "business_location")
    private String businessLocation;

    @Column(name = "technology")
    private String technology;

    @Column(name = "date_format")
    private String dateFormat;

    @Column(name = "field_separator")
    private String fieldSeparator;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "profile_url")
    private String profileUrl;

}
