package io.github.jhipster.web.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating a Page object.
 */
public interface PageUtil {

    /**
     * Create a {@link Page} from a {@link List} of objects
     *
     * @param list - list of objects
     * @param pageable
     * @param <T> - type of object
     * @return page containing objects, and attributes set according to pageable
     * @throws IllegalArgumentException - if list is null
     */
    static <T> Page<T> createPageFromList(List<T> list, Pageable pageable) {
        if (list == null) {
            throw new IllegalArgumentException("To create a Page, the list mustn't be null!");
        }

        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > list.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        int endOfPage = startOfPage + pageable.getPageSize();
        endOfPage = (endOfPage > list.size()) ? list.size() : endOfPage;
        return new PageImpl<>(list.subList(startOfPage, endOfPage), pageable, list.size());
    }
}
