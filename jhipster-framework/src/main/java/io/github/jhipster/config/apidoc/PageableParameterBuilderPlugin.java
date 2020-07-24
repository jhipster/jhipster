/*
 * Copyright 2016-2020 the original author or authors from the JHipster project.
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

package io.github.jhipster.config.apidoc;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * The Springfox Plugin to resolve {@link org.springframework.data.domain.Pageable} parameter into plain fields.
 */
public class PageableParameterBuilderPlugin implements OperationBuilderPlugin {

    /** Constant <code>DEFAULT_PAGE_NAME="page"</code> */
    public static final String DEFAULT_PAGE_NAME = "page";
    /** Constant <code>PAGE_DESCRIPTION="Page number of the requested page"</code> */
    public static final String PAGE_DESCRIPTION = "Page number of the requested page";

    /** Constant <code>DEFAULT_SIZE_NAME="size"</code> */
    public static final String DEFAULT_SIZE_NAME = "size";
    /** Constant <code>SIZE_DESCRIPTION="Size of a page"</code> */
    public static final String SIZE_DESCRIPTION = "Size of a page";

    /** Constant <code>DEFAULT_SORT_NAME="sort"</code> */
    public static final String DEFAULT_SORT_NAME = "sort";
    /** Constant <code>SORT_DESCRIPTION="Sorting criteria in the format: propert"{trunked}</code> */
    public static final String SORT_DESCRIPTION = "Sorting criteria in the format: property(,asc|desc). "
        + "Default sort order is ascending. "
        + "Multiple sort criteria are supported.";

    private final ResolvedType pageableType;

    /**
     * <p>Constructor for PageableParameterBuilderPlugin.</p>
     *
     * @param resolver a {@link com.fasterxml.classmate.TypeResolver} object.
     */
    public PageableParameterBuilderPlugin(TypeResolver resolver) {
        this.pageableType = resolver.resolve(Pageable.class);
    }

    /** {@inheritDoc} */
    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.OAS_30.equals(delimiter);
    }

    /** {@inheritDoc} */
    @Override
    public void apply(OperationContext context) {
        List<RequestParameter> parameters = new ArrayList<>();
        for (ResolvedMethodParameter methodParameter : context.getParameters()) {
            ResolvedType resolvedType = methodParameter.getParameterType();

            if (pageableType.equals(resolvedType)) {
                parameters.add(createPageParameter());
                parameters.add(createSizeParameter());
                parameters.add(createSortParameter());

                context.operationBuilder().requestParameters(parameters);
            }
        }
    }

    /**
     * Page name may be varied.
     * See {@link org.springframework.data.web.PageableHandlerMethodArgumentResolver#setPageParameterName(String)}
     *
     * @return The page parameter name
     */
    protected String getPageName() {
        return DEFAULT_PAGE_NAME;
    }

    /**
     * Size name may be varied.
     * See {@link org.springframework.data.web.PageableHandlerMethodArgumentResolver#setSizeParameterName(String)}
     *
     * @return The size parameter name
     */
    protected String getSizeName() {
        return DEFAULT_SIZE_NAME;
    }

    /**
     * Sort name may be varied.
     * See {@link org.springframework.data.web.SortHandlerMethodArgumentResolver#setSortParameter(String)}
     *
     * @return The sort parameter name
     */
    protected String getSortName() {
        return DEFAULT_SORT_NAME;
    }

    /**
     * Create a page parameter.
     * Override it if needed. Set a default value for example.
     *
     * @return The page parameter
     */
    protected RequestParameter createPageParameter() {
        return new RequestParameterBuilder()
            .name(getPageName())
            .in(ParameterType.QUERY)
            .query(p -> p.model(m -> m.scalarModel(ScalarType.INTEGER)))
            .description(PAGE_DESCRIPTION)
            .build();
    }

    /**
     * Create a size parameter.
     * Override it if needed. Set a default value for example.
     *
     * @return The size parameter
     */
    protected RequestParameter createSizeParameter() {
        return new RequestParameterBuilder()
            .name(getSizeName())
            .in(ParameterType.QUERY)
            .query(p -> p.model(m -> m.scalarModel(ScalarType.INTEGER)))
            .description(SIZE_DESCRIPTION)
            .build();
    }

    /**
     * Create a sort parameter.
     * Override it if needed. Set a default value or further description for example.
     *
     * @return The sort parameter
     */
    protected RequestParameter createSortParameter() {
        return new RequestParameterBuilder()
            .name(getSortName())
            .in(ParameterType.QUERY)
            .query(p -> p.model(m -> m.collectionModel(
                cm -> cm.model(m2 -> m2.scalarModel(ScalarType.STRING)))))
            .description(SORT_DESCRIPTION)
            .build();
    }
}
