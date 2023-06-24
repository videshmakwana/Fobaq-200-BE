package com.brilworks.accounts.config;

import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import com.brilworks.accounts.dto.AccessTokenModel;
import com.brilworks.accounts.dto.CustomUserDetail;
import com.brilworks.accounts.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    private final TokenStoreService tokenStoreService;

    private final String principal;
    private final List<GrantedAuthority> authorities;

    public AuthenticationTokenProcessingFilter(TokenStoreService tokenStoreService, String authority,
                                               String principal) {
        this.tokenStoreService = tokenStoreService;
        this.principal = principal;
        this.authorities = AuthorityUtils.createAuthorityList(authority);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String reqId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        try {
            MDC.put(Constants.REQUEST_ID, reqId);
            MDC.put(Constants.USER_NAME, Constants.NA);


            if (!(request instanceof HttpServletRequest)) {
                throw new RuntimeException("Expecting a HTTP request");
            }

            HttpServletRequest httpRequest = (HttpServletRequest) request;

            String authToken = null;
            if (StringUtils.isNotBlank(httpRequest.getHeader(Constants.AUTHORIZATION))) {
                authToken = httpRequest.getHeader(Constants.AUTHORIZATION);
            }
            AccessTokenModel objUserDetails = null;
            UserDetails userDetails = new CustomUserDetail(this.principal, Constants.STRING_EMPTY, this.authorities, null);
            if (StringUtils.isNotBlank(authToken)) {
                try {
                    objUserDetails = this.tokenStoreService.readAccessToken(authToken);
                } catch (Exception exception) {
                    logger.error("error while reading access token", exception);
                }
                if (objUserDetails != null) {
                    List<GrantedAuthority> objAuthorities = new ArrayList<>();
                    userDetails = new CustomUserDetail(objUserDetails.getUsername(), objUserDetails.getUser().getPassword(),
                            true, true, true, true,
                            objAuthorities, objUserDetails.getUser());
                    MDC.put(Constants.USER_NAME, userDetails.getUsername());
                }
            } else {
                HttpSession session = httpRequest.getSession(false);
                if (session != null && session.getAttribute(Constants.USER_DETAILS) != null) {
                    userDetails = (UserDetails) session.getAttribute(Constants.USER_DETAILS);
                    if (userDetails != null) {
                        MDC.put(Constants.USER_NAME, userDetails.getUsername());
                    }
                }
            }
            setAuthentication(userDetails, httpRequest);

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            // Allow cross domain
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            // Allow set custom header token
            httpResponse.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, x-requested-with, X-Custom-Header, Authorization");
            // Server header relieve the server info so set it to blank.
            httpResponse.setHeader(Constants.SERVER, "");
            if (httpRequest.getRequestURL().indexOf("/rest/brilcrm/ws") > -1 || httpRequest.getRequestURL().indexOf("/websocket/info") > -1) {
                httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader(Constants.ORIGIN));
                httpResponse.setHeader("Access-Control-Allow-Credentials", Constants.TRUE);
            }

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }

    }

    private void setAuthentication(UserDetails objUserDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(objUserDetails,
                null, objUserDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
