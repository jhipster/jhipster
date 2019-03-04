/*
 * Copyright 2018-2019 the original author or authors.
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

package io.github.jhipster.service.demo;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;

public class BaseEntityQueryService extends QueryService<BaseEntity> {

    static class BaseEntityCriteria {
        LongFilter id;
        StringFilter name;

        public LongFilter getId() {
            return id;
        }

        public StringFilter getName() {
            return name;
        }
    }

    public Specification<BaseEntity> createSpecification(BaseEntityCriteria criteria) {
        Specification<BaseEntity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BaseEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BaseEntity_.name));
            }
        }
        return specification;
    }
}
