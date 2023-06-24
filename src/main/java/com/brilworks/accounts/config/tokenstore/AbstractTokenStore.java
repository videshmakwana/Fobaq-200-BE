package com.brilworks.accounts.config.tokenstore;

import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTokenStore implements TokenStoreService {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_REMEMBER = "remember";
	static final String CLAIM_EXP_DATE = "expdate";

	private static final String AUDIENCE_WEB = "web";

	private String secret = "brilworks";

	protected long expirationToken = 24 * 3600 * 1000;

	public void removeClaim(String token) {
		Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			claims.put(CLAIM_KEY_USERNAME, null);
			claims.clear();
		}
	}

	private String generateToken(Map<String, Object> claims) {
		claims.put(CLAIM_EXP_DATE, generateExpirationDate(false));//remember me not implemeted
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Date generateExpirationDate(boolean rememberme) {
		if (rememberme) {
			return new Date(System.currentTimeMillis() + Integer.MAX_VALUE);
		} else {
			return new Date(System.currentTimeMillis() + expirationToken);
		}
	}

	public abstract void updateTokenWithUserData(String oldToken, String newToken, Date expireDate,
			AccessTokenModel accessTokenModel);


	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userName);
		claims.put(CLAIM_KEY_AUDIENCE, AUDIENCE_WEB);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Boolean validateToken(String token) {
		return (isTokenExist(token) && !isTokenExpired(token));
	}

	protected Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		if (expiration != null) {
			return expiration.before(new Date());
		} else {
			removeToken(token);
			return true;
		}
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = new Date((long) claims.get(CLAIM_EXP_DATE));
			if (new Date().after(expiration)) {
				expiration = null;
			}
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public Date getExpirationDateFromTokenWithoutNull(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = new Date((long) claims.get(CLAIM_EXP_DATE));
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	@Override
	public void updateUser(User user, String token) {
		AccessTokenModel accessTokenModel = this.readAccessToken(token);
		accessTokenModel.setUser(user);
		this.updateToken(token, accessTokenModel, false);
	}

	@Override
	public String updateOrganization(Long organizationId, String token) {
		AccessTokenModel accessTokenModel = this.readAccessToken(token);
		accessTokenModel.setOrganizationId(organizationId);
		this.updateToken(token, accessTokenModel, false);
		return token;
	}

}
