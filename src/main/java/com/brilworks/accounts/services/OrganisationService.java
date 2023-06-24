package com.brilworks.accounts.services;

import com.brilworks.accounts.dto.OrganisationDto;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface OrganisationService {

    OrganisationDto getOrganisationByUrl(String url);

    Organisation getOrganisation(String url);

    Optional<Organisation> getOrganisationOpt(String url);

    Organisation doCreateOrganisationByName(String organisationUrl, User user);

    OrganisationDto findById(Long organisationId);

    void updateOrganisation(OrganisationDto organisationDto);

    void deleteOrganisation(String organisationUrl, Long id);

    List<OrganisationDto> getListOfOrganisations(Long id);

    List<String> permissionHavingUsers(Long organizationId, Long userId);
}
