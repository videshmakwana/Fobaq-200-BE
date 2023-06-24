package com.brilworks.accounts.config.tokenstore;

import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.dto.LoginResponse;
import com.brilworks.accounts.exception.TokenException;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenStoreService extends AbstractTokenStore implements TokenStoreService {

    private ConcurrentHashMap<String, Object> token = new ConcurrentHashMap<>();

    public void removeToken(String accessToken) {
        this.token.remove(accessToken);
    }

    public AccessTokenModel readAccessToken(String accessToken) {
        AccessTokenModel accessTokenModel = null;
        if (Boolean.TRUE.equals(validateToken(accessToken))) {
            accessTokenModel = (AccessTokenModel) this.token.get(accessToken);
        }
        return accessTokenModel;
    }

    public LoginResponse generateAccessToken(AccessTokenModel accessTokenModel) {
        String accessToken = generateToken(accessTokenModel.getUsername());
        if (null != accessToken) {
            this.token.put(accessToken, accessTokenModel);
            return new LoginResponse(accessTokenModel.getUser().getId(), accessTokenModel.getUser().getEmail(), accessToken, accessTokenModel.getUser().getUserName(), accessTokenModel.getOrgId());
        } else {
            throw new TokenException(TokenException.Exceptions.ERROR_IN_GENERATION_OF_TOKEN);
        }
    }


    @Override
    public boolean isTokenExist(String accessToken) {
        return this.token.containsKey(accessToken);
    }

    @Override
    public boolean updateToken(String token, AccessTokenModel accessTokenModel, boolean calledFromIsTokenExists) {
        this.token.put(token, accessTokenModel);
        return true;
    }


    @Override
    public void updateTokenWithUserData(String oldToken, String newToken, Date expireDate,
                                        AccessTokenModel accessTokenModel) {
        removeClaim(oldToken);
        this.token.remove(oldToken);
        this.token.put(newToken, accessTokenModel);
    }

    @Override
    public boolean checkAccountLocked(String username) {
        Integer count = getInvalidLoginCount(username);
        if (count == null) count = 0;

        return count >= 20;
    }


    @Override
    public Object getData(String key) {
        return this.token.get(key);
    }

    @Override
    public void incrementInvalidPasswordCount(String username) {
        Integer count = getInvalidLoginCount(username);
        if (count == null) count = 0;

        count = count + 1;

        this.token.put("INVALID_PASSWORD_" + username, count);
    }

    @Override
    public boolean rateLimitSuccess(String key, int limit) {
        Integer apiCallCount = (Integer) this.token.get(key);
        if (apiCallCount != null) {
            apiCallCount = 0;
        }
        if (apiCallCount >= limit) {
            return false;
        } else {
            apiCallCount++;
            this.token.put(key, apiCallCount);
            return true;
        }
    }

    private Integer getInvalidLoginCount(String username) {
        return (Integer) this.token.get("INVALID_PASSWORD_" + username);
    }

    @Override
    public AccessTokenModel getAccessToken(String accessToken) {
        return (AccessTokenModel) this.token.get(accessToken);
    }

    @Override
    public void deleteUserFromInvalidPasswordMap(String email) {

    }
}