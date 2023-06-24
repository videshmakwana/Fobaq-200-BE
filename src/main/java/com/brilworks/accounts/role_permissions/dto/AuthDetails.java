package com.brilworks.accounts.role_permissions.dto;

import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDetails {

    private Long organizationId;
    private Long userId;
    private UserBasicInfo userBasicInfo;

    public AuthDetails(AccessTokenModel accessTokenModel) {
        setOrganizationId(null != accessTokenModel.getOrgId() ? accessTokenModel.getOrgId() : accessTokenModel.getOrganizationId());
        setUserId(accessTokenModel.getUserId());
        User user = accessTokenModel.getUser();
        setUserBasicInfo(new UserBasicInfo(user));
    }


    public void setUserBasicInfo(UserBasicInfo userBasicInfo) {
        this.userBasicInfo = userBasicInfo;
    }
}
