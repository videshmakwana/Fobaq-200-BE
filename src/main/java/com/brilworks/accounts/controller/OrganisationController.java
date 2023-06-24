package com.brilworks.accounts.controller;

import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.OrganisationDto;
import com.brilworks.accounts.dto.ResponseDto;

import com.brilworks.accounts.dto.UserDto;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.role_permissions.dto.AuthDetails;
import com.brilworks.accounts.role_permissions.dto.RoleDto;
import com.brilworks.accounts.role_permissions.services.RoleService;
import com.brilworks.accounts.services.AuthService;
import com.brilworks.accounts.services.OrganisationService;
import com.brilworks.accounts.services.impl.UserServiceImpl;
import com.brilworks.accounts.utils.Constants;
import com.brilworks.accounts.utils.FileUploadHelper;
import com.brilworks.accounts.utils.FileUploadS3Bucket;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@SecurityRequirement(name = "brilworks")
@RequestMapping("/rest/accounts/{org}")
@RestController
public class OrganisationController {
    @Autowired
    private OrganisationService organizationService;
    @Autowired
    private AuthValidator authValidator;
    @Autowired
    private FileUploadHelper fileUploadHelper;
    @Autowired
    private FileUploadS3Bucket fileUploadS3Bucket;
    @Autowired
    private TokenStoreService tokenService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/userProfile")
    public User getEmployeeInfoById(@PathVariable("org") String organisationUrl,
                                    Authentication auth) {
        User user = authValidator.authUser(auth);
        user.setPassword(null);
        return user;
    }

    @GetMapping("/module/{type}/userProfile")
    public UserDto getEmployeeInfoById(@PathVariable("type") String moduleType, HttpServletRequest httpServletRequest) {
        AuthDetails authDetails = authService.getAuthDetails(httpServletRequest);
        UserDto user = userService.findById(authDetails.getUserId());
        user.setPassword(null);
        List<RoleDto> userRole = roleService.getRolesByEmpIdAndModule(authDetails.getUserId(), moduleType);
        for (RoleDto userRole1 : userRole) {
            user.setRoleId(userRole1.getRoleId());
        }
        return user;
    }

    @PutMapping("/setOrg")
    public String setOrganization(@PathVariable("org") String organisationUrl,
                                Authentication auth,
                                HttpServletRequest httpRequest) {
        String authToken = httpRequest.getHeader("Authorization");
        authValidator.authUser(auth);
        OrganisationDto organisationDto = organizationService.getOrganisationByUrl(organisationUrl);

        return tokenService.updateOrganization(organisationDto.getOrganizationId(), authToken);
    }

    @GetMapping
    public OrganisationDto getOrganisationByUrl(@PathVariable("org") String organisationUrl, Authentication auth) {
        authValidator.authUser(auth);
        return organizationService.getOrganisationByUrl(organisationUrl);
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication auth) {
        authValidator.authUser(auth);
        return fileUploadS3Bucket.uploadFileToS3Bucket(file, "OrganisationImages");
    }

    @PutMapping
    public ResponseDto updateOrganisation(@RequestBody OrganisationDto organisationDto, Authentication auth) {
        authValidator.authUser(auth);
        organizationService.updateOrganisation(organisationDto);
        return new ResponseDto(Constants.SUCCESS, Constants.SAVED);
    }

    @DeleteMapping("/{orgId}")
    public ResponseDto deleteOrganisation(@PathVariable("org") String organisationUrl, Authentication auth, @PathVariable("orgId") Long id) {
        organizationService.deleteOrganisation(organisationUrl, id);
        return new ResponseDto(Constants.SUCCESS, Constants.SAVED);
    }

    @GetMapping("/getOrgInfo")
    public OrganisationDto getOrganisationByUrl(@PathVariable("org") String organisationUrl) {
        return organizationService.getOrganisationByUrl(organisationUrl);
    }

    @GetMapping("/userPermissions")
    public List<String> permissionHavingUsers(@PathVariable("org") String organisationUrl, Authentication auth) {
        User user = authValidator.authUser(auth);
        OrganisationDto organisationByUrl = organizationService.getOrganisationByUrl(organisationUrl);
        return organizationService.permissionHavingUsers(organisationByUrl.getOrganizationId(), user.getId());
    }
}
