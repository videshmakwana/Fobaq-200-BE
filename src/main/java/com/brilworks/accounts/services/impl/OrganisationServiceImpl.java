package com.brilworks.accounts.services.impl;

import com.brilworks.accounts.dto.OrganisationDto;
import com.brilworks.accounts.entity.Organisation;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.exception.ConflictException;
import com.brilworks.accounts.exception.NotAcceptableException;
import com.brilworks.accounts.exception.NotFoundException;
import com.brilworks.accounts.repository.OrganisationRepository;
import com.brilworks.accounts.role_permissions.repository.RolePermissionMappingRepository;
import com.brilworks.accounts.role_permissions.services.UserRoleService;
import com.brilworks.accounts.services.OrganisationService;
import com.brilworks.accounts.services.UserService;
import com.brilworks.accounts.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    private OrganisationRepository organizationRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionMappingRepository rolePermissionMappingRepository;

    @Autowired
    private UserService userService;



    public OrganisationDto findById(Long id) {
        Optional<Organisation> organisation = organizationRepository.findById(id);
        if (organisation.isEmpty()) {
            throw new NotFoundException(NotFoundException.NotFound.ORGANISATION_NOT_FOUND);
        }
        return new OrganisationDto().toDto(organisation.get());
    }

    @Override
    public void updateOrganisation(OrganisationDto organisationDto) {
        if (organisationDto == null) {
            throw new NotFoundException(NotFoundException.NotFound.VALUE_MISSING);
        }
        Organisation organisation = organisationDto.toEntity(organisationDto);
        organizationRepository.save(organisation);

    }

    @Override
    public void deleteOrganisation(String organisationUrl, Long id) {
        OrganisationDto organisation = getOrganisationByUrl(organisationUrl);
        if (organisation.getOrganizationId() == null) {
            throw new NotFoundException(NotFoundException.NotFound.VALUE_MISSING);
        }
        organizationRepository.deleteById(id);
        //  OrganisationEmpMapping organisationEmpMapping = organisationEmpMappingService.getOrganizationEmpMapping(empId, organisation.getOrganizationId());
        // organisationEmpMapping.setStatus(EmployeeStatus.DELETED);
        // organisationEmpMappingService.updateOrganisationEmpMappingService(organisationEmpMapping);
    }

    @Override
    public List<OrganisationDto> getListOfOrganisations(Long id) {
        return organizationRepository.getListOfOrganisations(id);
    }

    @Override
    public OrganisationDto getOrganisationByUrl(String url) {
        url = URLEncoder.encode(url, StandardCharsets.UTF_8);
        return new OrganisationDto().toDto(getOrganisation(url));
    }

    @Override
    public Organisation getOrganisation(String url) {
        return getOrganisationOpt(url).orElseThrow(() -> new NotFoundException(NotFoundException.OrganisationNotFound.ORGANISATION_NOT_FOUND));
    }

    @Override
    public Optional<Organisation> getOrganisationOpt(String url) {
        return organizationRepository.findOrganisationByOrganisationUrl(url);
    }


    private String checkOrgPageUrlAlreadyExists(String organisationName) {
        String organizerUrl = organisationName.replaceAll(Constants.ATOZ_BOTHCASE_AND_NUMBERS, Constants.STRING_EMPTY);
        Optional<Organisation> optionalOrganizer = getOrganisationOpt(organizerUrl);
        if (optionalOrganizer.isPresent()) {
            organizerUrl = this.getOrganisationUrl(organizerUrl, 0, organizerUrl.length(), false);
        }
        return organizerUrl;
    }

    public String getOrganisationUrl(String url, int count, int urlLength, boolean updateUrl) {
        // remove other characters from url then alphanumeric
        if (!StringUtils.isAlphanumeric(url) && updateUrl) {
            throw new NotAcceptableException(NotAcceptableException.NotAceptableExeceptionMSG.URL_NOT_SUPPORT_SPECIAL_CHART);
        }
        OrganisationDto organizer;
        try {
            organizer = this.getOrganisationByUrl(url);
        } catch (NotFoundException nfe) {
            if (StringUtils.equals(nfe.getErrorCode(), NotFoundException.OrganisationNotFound.ORGANISATION_NOT_FOUND.getStatusCode())) {
                organizer = null;
            } else {
                throw nfe;
            }
        }
        if (organizer != null && updateUrl) {
            throw new ConflictException(ConflictException.UserExceptionConflictMsg.ORGANIZER_URL_ALREADY_EXIST);
        }
        if (organizer == null) {
            return url;
        } else {
            return getOrganisationUrl(url.substring(0, urlLength) + count, count + 1, urlLength, false);
        }
    }

    @Override
    public Organisation doCreateOrganisationByName(String organisationName, User user) {
        Organisation organisation = new Organisation();
        organisation.setName(organisationName);
        organisation.setOrganisationUrl(this.checkOrgPageUrlAlreadyExists(organisationName));
        return organizationRepository.save(organisation);
    }

    @Override
    public List<String> permissionHavingUsers(Long userId, Long organizationId){
        User user = userService.getUserById(userId);
        List<Long> userRolesIds = userRoleService.getUserRoldIdsInOrg(user.getId(), organizationId);

        if (!userRolesIds .isEmpty()) {
            return rolePermissionMappingRepository.findAllPermissions(userRolesIds).stream().map(Enum::toString).collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }


}
