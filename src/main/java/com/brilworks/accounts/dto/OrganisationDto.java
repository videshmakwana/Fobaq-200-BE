package com.brilworks.accounts.dto;

import com.brilworks.accounts.entity.Organisation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrganisationDto {

    private Long organizationId;
    private String organisationName;
    private String businessLocation;
    private String organisationUrl;
    private String technology;
    private String dateFormat;
    private String fieldSeparator;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String city;
    private String pinCode;
    private String profileUrl;
    private Date createdAt;

    public Organisation toEntity(OrganisationDto organizationDto) {
        Organisation organisation = new Organisation();
        organisation.setId(organizationDto.getOrganizationId());
        organisation.setName(organizationDto.getOrganisationName());
        organisation.setOrganisationUrl(organizationDto.getOrganisationUrl());
        organisation.setBusinessLocation(organizationDto.getBusinessLocation());
        organisation.setTechnology(organizationDto.getTechnology());
        organisation.setDateFormat(organizationDto.getDateFormat());
        organisation.setFieldSeparator(organizationDto.getFieldSeparator());
        organisation.setAddressLine1(organizationDto.getAddressLine1());
        organisation.setProfileUrl(organizationDto.getProfileUrl());
        organisation.setAddressLine2(organizationDto.getAddressLine2());
        organisation.setState(organizationDto.getState());
        organisation.setCity(organizationDto.getCity());
        organisation.setPinCode(organizationDto.getPinCode());
        if (organizationDto.getCreatedAt() == null) {
            organisation.setCreatedAt(organizationDto.getCreatedAt());
        }
        organisation.setUpdatedAt(new Date());
        return organisation;
    }

    public OrganisationDto toDto(Organisation organisation) {
        OrganisationDto organizationDto = new OrganisationDto();
        organizationDto.setOrganizationId(organisation.getId());
        organizationDto.setOrganisationName(organisation.getName());
        organizationDto.setBusinessLocation(organisation.getBusinessLocation());
        organizationDto.setTechnology(organisation.getTechnology());
        organizationDto.setDateFormat(organisation.getDateFormat());
        organizationDto.setFieldSeparator(organisation.getFieldSeparator());
        organizationDto.setProfileUrl(organisation.getProfileUrl());
        organizationDto.setAddressLine1(organisation.getAddressLine1());
        organizationDto.setAddressLine2(organisation.getAddressLine2());
        organizationDto.setState(organisation.getState());
        organizationDto.setCity(organisation.getCity());
        organizationDto.setPinCode(organisation.getPinCode());
        organizationDto.setOrganisationUrl(organisation.getOrganisationUrl());
        organizationDto.setCreatedAt(organisation.getCreatedAt());
        return organizationDto;
    }

    public OrganisationDto(Organisation organisation) {
        this.organizationId = organisation.getId();
        this.organisationName = organisation.getName();
        this.businessLocation = organisation.getBusinessLocation();
        this.organisationUrl = organisation.getOrganisationUrl();
        this.technology = organisation.getTechnology();
        this.dateFormat = organisation.getDateFormat();
        this.fieldSeparator = organisation.getFieldSeparator();
        this.addressLine1 = organisation.getAddressLine1();
        this.addressLine2 = organisation.getAddressLine2();
        this.profileUrl = organisation.getProfileUrl();
        this.state = organisation.getState();
        this.city = organisation.getCity();
        this.pinCode = organisation.getPinCode();
        this.createdAt = organisation.getCreatedAt();
    }


}
