package io.github.jhipster.web.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PageUtilTest {

    private static final int PAGE_SIZE = 20;
    private static final int TOTAL_ELEMENTS_OF_3 = 3;
    private static final int TOTAL_ELEMENTS_OF_40 = 40;
    private static final int TOTAL_PAGES_OF_1 = 1;
    private static final int TOTAL_PAGES_OF_2 = 2;

    private List<Integer> content;

    @BeforeEach
    public void setup() {
        content = new ArrayList<>();
    }

    @Test
    public void generatePageFromListTestShouldCreatePage() {
        content.add(1);
        content.add(2);
        content.add(3);

        Page<Integer> page = PageUtil.createPageFromList(content, PageRequest.of(0, PAGE_SIZE));

        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(TOTAL_ELEMENTS_OF_3);
        assertThat(page.getTotalPages()).isEqualTo(TOTAL_PAGES_OF_1);
    }

    @Test
    public void generatePageFromListShouldCreatePageWithTwoTotalPages() {
        for (int i = 0; i < TOTAL_ELEMENTS_OF_40; i++) {
            content.add(i);
        }

        Page<Integer> page = PageUtil.createPageFromList(content, PageRequest.of(0, PAGE_SIZE));

        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(TOTAL_ELEMENTS_OF_40);
        assertThat(page.getTotalPages()).isEqualTo(TOTAL_PAGES_OF_2);
    }

    @Test
    public void generatePageFromListShouldThrowIllegalArgumentExceptionIfListNull() {
        assertThatThrownBy(() -> PageUtil.createPageFromList(null, PageRequest.of(0, PAGE_SIZE)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
