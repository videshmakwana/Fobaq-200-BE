package com.brilworks.accounts.dto;

import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.role_permissions.enums.PermissionsEnum;

import java.io.Serializable;
import java.util.List;

public class AccessTokenModel extends AuthDetails implements Serializable {
	private static final long serialVersionUID = -4263112003385658152L;
	private String username;
	private String password;
	private User user;
	private Long orgId;
	private List<PermissionsEnum> permissionList;

	public List<PermissionsEnum> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermissionsEnum> permissionList) {
		this.permissionList = permissionList;
	}

    public AccessTokenModel() {
    }

	public AccessTokenModel(String username, String password, User user, Long orgId, List<PermissionsEnum> permissionList) {
		this.username = username;
		this.password = password;
		this.orgId = orgId;
		setUser(user);
		setPermissionList(permissionList);
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.setUserId(getUser().getId());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
