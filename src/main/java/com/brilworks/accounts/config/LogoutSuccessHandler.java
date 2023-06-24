package com.brilworks.accounts.config;

import com.brilworks.accounts.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String eventBaseUrl = request.getParameter(Constants.EVENT_BASE_URL);
        String targetUrl = null;
        if (authentication == null) {
            targetUrl = String.format("%s/u/login", Constants.UI_BASE_URL);
        } else if (StringUtils.isNotBlank(eventBaseUrl)) {
            targetUrl = String.format("%s/events/%s", Constants.UI_BASE_URL, eventBaseUrl);
        } else {
            targetUrl = "Minal";
        }
        response.sendRedirect(targetUrl);
        super.onLogoutSuccess(request, response, authentication);
    }

}
