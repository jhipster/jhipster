package io.github.jhipster.config.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This encoder adds support for pageable, which will be applied to the query parameters.
 * see issue https://github.com/spring-cloud/spring-cloud-netflix/issues/556
 * Copied from https://github.com/ElderByte-/java-starter/blob/master/starter-spring-cloud/src/main/java/com/elderbyte/spring/cloud/bootstrap/feign/DefaultFeignConfiguration.java
 */
@ConditionalOnClass(FeignClientsConfiguration.class)
public class PageableQueryEncoder implements Encoder {

    private final Encoder delegate;

    public PageableQueryEncoder(Encoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {

        if (object instanceof Pageable) {
            Pageable pageable = (Pageable) object;
            template.query("page", pageable.getPageNumber() + "");
            template.query("size", pageable.getPageSize() + "");
            if (pageable.getSort() != null) {
                applySort(template, pageable.getSort());
            }
        } else if(object instanceof Sort) {
            Sort sort = (Sort)object;
            applySort(template, sort);
        } else {
            delegate.encode(object, bodyType, template);
        }
    }


    private void applySort(RequestTemplate template, Sort sort) {
        Collection<String> existingSorts = template.queries().get("sort");
        List<String> sortQueries  = existingSorts != null ? new ArrayList<>(existingSorts) : new ArrayList<>();
        for (Sort.Order order : sort) {
            sortQueries.add(order.getProperty() + "," + order.getDirection());
        }
        if(!sortQueries.isEmpty()) {
            template.query("sort", sortQueries);
        }
    }
}
