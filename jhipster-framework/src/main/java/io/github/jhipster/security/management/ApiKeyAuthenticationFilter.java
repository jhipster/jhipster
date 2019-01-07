/*
 * Copyright 2016-2019 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.security.management;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and install the "manager" Spring Security principal if the Authorization header
 * contains the correct API Key.
 */
public class ApiKeyAuthenticationFilter extends GenericFilterBean {
    static final String AUTHORIZATION_HEADER = "Authorization";
    static final String MANAGER = "manager";
    static final String ROLE_MANAGEMENT = "ROLE_MANAGEMENT";
    static final SimpleGrantedAuthority MANAGEMENT_AUTHORITY = new SimpleGrantedAuthority(ROLE_MANAGEMENT);
    private final String managementApiKey;

    public ApiKeyAuthenticationFilter(String managementApiKey) {
        this.managementApiKey = managementApiKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authorizationKey = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authorizationKey) && authorizationKey.equals(managementApiKey)) {
            Authentication authentication = getManagerAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Authentication getManagerAuthentication() {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(MANAGEMENT_AUTHORITY);
        User principal = new User(MANAGER, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
