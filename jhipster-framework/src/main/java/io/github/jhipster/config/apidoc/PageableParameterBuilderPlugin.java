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
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

/**
 * The Springfox Plugin to resolve {@link org.springframework.data.domain.Pageable} parameter into plain fields.
 */
public class PageableParameterBuilderPlugin implements OperationBuilderPlugin {

    /** Constant <code>DEFAULT_PAGE_NAME="page"</code> */
    public static final String DEFAULT_PAGE_NAME = "page";
    /** Constant <code>PAGE_TYPE="query"</code> */
    public static final String PAGE_TYPE = "query";
    /** Constant <code>PAGE_DESCRIPTION="Page number of the requested page"</code> */
    public static final String PAGE_DESCRIPTION = "Page number of the requested page";

    /** Constant <code>DEFAULT_SIZE_NAME="size"</code> */
    public static final String DEFAULT_SIZE_NAME = "size";
    /** Constant <code>SIZE_TYPE="query"</code> */
    public static final String SIZE_TYPE = "query";
    /** Constant <code>SIZE_DESCRIPTION="Size of a page"</code> */
    public static final String SIZE_DESCRIPTION = "Size of a page";

    /** Constant <code>DEFAULT_SORT_NAME="sort"</code> */
    public static final String DEFAULT_SORT_NAME = "sort";
    /** Constant <code>SORT_TYPE="query"</code> */
    public static final String SORT_TYPE = "query";
    /** Constant <code>SORT_DESCRIPTION="Sorting criteria in the format: propert"{trunked}</code> */
    public static final String SORT_DESCRIPTION = "Sorting criteria in the format: property(,asc|desc). "
        + "Default sort order is ascending. "
        + "Multiple sort criteria are supported.";

    private final TypeNameExtractor nameExtractor;
    private final TypeResolver resolver;
    private final ResolvedType pageableType;

    /**
     * <p>Constructor for PageableParameterBuilderPlugin.</p>
     *
     * @param nameExtractor a {@link springfox.documentation.schema.TypeNameExtractor} object.
     * @param resolver a {@link com.fasterxml.classmate.TypeResolver} object.
     */
    public PageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        this.nameExtractor = nameExtractor;
        this.resolver = resolver;
        this.pageableType = resolver.resolve(Pageable.class);
    }

    /** {@inheritDoc} */
    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }

    /** {@inheritDoc} */
    @Override
    public void apply(OperationContext context) {
        List<Parameter> parameters = newArrayList();
        for (ResolvedMethodParameter methodParameter : context.getParameters()) {
            ResolvedType resolvedType = methodParameter.getParameterType();

            if (pageableType.equals(resolvedType)) {
                ParameterContext parameterContext = new ParameterContext(methodParameter,
                    new ParameterBuilder(),
                    context.getDocumentationContext(),
                    context.getGenericsNamingStrategy(),
                    context);

                parameters.add(createPageParameter(parameterContext));
                parameters.add(createSizeParameter(parameterContext));
                parameters.add(createSortParameter(parameterContext));

                context.operationBuilder().parameters(parameters);
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
     * @param context {@link org.springframework.data.domain.Pageable} parameter context
     * @return The page parameter
     */
    protected Parameter createPageParameter(ParameterContext context) {
        ModelReference intModel = createModelRefFactory(context).apply(resolver.resolve(Integer.TYPE));
        return new ParameterBuilder()
            .name(getPageName())
            .parameterType(PAGE_TYPE)
            .modelRef(intModel)
            .description(PAGE_DESCRIPTION)
            .build();
    }

    /**
     * Create a size parameter.
     * Override it if needed. Set a default value for example.
     *
     * @param context {@link org.springframework.data.domain.Pageable} parameter context
     * @return The size parameter
     */
    protected Parameter createSizeParameter(ParameterContext context) {
        ModelReference intModel = createModelRefFactory(context).apply(resolver.resolve(Integer.TYPE));
        return new ParameterBuilder()
            .name(getSizeName())
            .parameterType(SIZE_TYPE)
            .modelRef(intModel)
            .description(SIZE_DESCRIPTION)
            .build();
    }

    /**
     * Create a sort parameter.
     * Override it if needed. Set a default value or further description for example.
     *
     * @param context {@link org.springframework.data.domain.Pageable} parameter context
     * @return The sort parameter
     */
    protected Parameter createSortParameter(ParameterContext context) {
        ModelReference stringModel = createModelRefFactory(context).apply(resolver.resolve(List.class, String.class));
        return new ParameterBuilder()
            .name(getSortName())
            .parameterType(SORT_TYPE)
            .modelRef(stringModel)
            .allowMultiple(true)
            .description(SORT_DESCRIPTION)
            .build();
    }

    /**
     * <p>createModelRefFactory.</p>
     *
     * @param context a {@link springfox.documentation.spi.service.contexts.ParameterContext} object.
     * @return a {@link java.util.function.Function} object.
     */
    protected Function<ResolvedType, ? extends ModelReference> createModelRefFactory(ParameterContext context) {
        ModelContext modelContext = inputParam(
            context.getGroupName(),
            context.resolvedMethodParameter().getParameterType(),
            context.getDocumentationType(),
            context.getAlternateTypeProvider(),
            context.getGenericNamingStrategy(),
            context.getIgnorableParameterTypes());
        return modelRefFactory(modelContext, nameExtractor);
    }

    TypeResolver getResolver() {
        return resolver;
    }

    TypeNameExtractor getNameExtractor() {
        return nameExtractor;
    }
}
