package io.github.jhipster.web.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PageUtilTest {

    private static final int PAGE_SIZE = 20;
    private static final int TOTAL_ELEMENTS_OF_3 = 3;
    private static final int TOTAL_ELEMENTS_OF_40 = 40;
    private static final int TOTAL_PAGES_OF_1 = 1;
    private static final int TOTAL_PAGES_OF_2 = 2;

    private List<Integer> content;

    @Before
    public void setup() {
        content = new ArrayList<>();
    }

    @Test
    public void generatePageFromListTestShouldCreatePage() {
        content.add(1);
        content.add(2);
        content.add(3);

        Page<Integer> page = PageUtil.createPageFromList(content, PageRequest.of(0, PAGE_SIZE));

        assertNotNull(page);
        assertEquals(PAGE_SIZE, page.getSize());
        assertEquals(TOTAL_ELEMENTS_OF_3, page.getTotalElements());
        assertEquals(TOTAL_PAGES_OF_1, page.getTotalPages());
    }

    @Test
    public void generatePageFromListShouldCreatePageWithTwoTotalPages() {
        for (int i = 0; i < TOTAL_ELEMENTS_OF_40; i++) {
            content.add(i);
        }

        Page<Integer> page = PageUtil.createPageFromList(content, PageRequest.of(0, PAGE_SIZE));

        assertNotNull(page);
        assertEquals(PAGE_SIZE, page.getSize());
        assertEquals(TOTAL_ELEMENTS_OF_40, page.getTotalElements());
        assertEquals(TOTAL_PAGES_OF_2, page.getTotalPages());
    }

    @Test(expected = IllegalArgumentException.class)
    public void generatePageFromListShouldThrowIllegalArgumentExceptionIfListNull() {
        PageUtil.createPageFromList(null, PageRequest.of(0, PAGE_SIZE));
    }
}
