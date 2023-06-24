package com.brilworks.accounts.config.tokenstore;

import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.dto.LoginResponse;
import com.brilworks.accounts.entity.User;

public interface TokenStoreService {

	AccessTokenModel readAccessToken(String accessToken);


	LoginResponse generateAccessToken(AccessTokenModel accessTokenModel);

	void removeToken(String accessToken);

	boolean isTokenExist(String accessToken);

	void updateUser(User user, String token);

	boolean updateToken(String token, AccessTokenModel accessTokenModel, boolean calledFromIsTokenExists);

	boolean checkAccountLocked(String username);


	Object getData(String key);

	void incrementInvalidPasswordCount(String username);

    boolean rateLimitSuccess(String key, int limit);

    AccessTokenModel getAccessToken(String accessToken);

	void deleteUserFromInvalidPasswordMap(String email);

	String updateOrganization(Long organizationId, String token);
}
