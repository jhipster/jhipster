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
package io.github.jhipster.web.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class PaginationUtil {

    private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    private static final String HEADER_LINK_FORMAT = "<{0}>; rel=\"{1}\"";

    private PaginationUtil() {
    }

    /**
     * Generate pagination headers for a Spring Data {@link Page} object.
     */
    public static <T> HttpHeaders generatePaginationHttpHeaders(HttpServletRequest request, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        StringBuilder link = new StringBuilder();
        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(request, pageNumber + 1, pageSize, "next"))
                .append(",");
        }
        if (pageNumber > 0) {
            link.append(prepareLink(request, pageNumber - 1, pageSize, "prev"))
                .append(",");
        }
        link.append(prepareLink(request, page.getTotalPages() - 1, pageSize, "last"))
            .append(",")
            .append(prepareLink(request, 0, pageSize, "first"));
        headers.add(HttpHeaders.LINK, link.toString());
        return headers;
    }

    private static String prepareLink(HttpServletRequest request, int pageNumber, int pageSize, String relType) {
        return MessageFormat.format(HEADER_LINK_FORMAT, preparePageUri(request, pageNumber, pageSize), relType);
    }

    private static String preparePageUri(HttpServletRequest request, int pageNumber, int pageSize) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.set("page", Integer.toString(pageNumber));
        parameters.set("size", Integer.toString(pageSize));
        request.getParameterMap().entrySet().stream()
            .filter(map -> !"page".equalsIgnoreCase(map.getKey()) && !"size".equalsIgnoreCase(map.getKey()))
            .forEach(map -> Arrays.asList(map.getValue()).forEach(value -> {
                    try {
                        parameters.add(map.getKey(), URLEncoder.encode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
            );
        return UriComponentsBuilder.fromUriString(request.getRequestURI())
            .queryParams(parameters).build(false)
            .toUriString();
    }
}
