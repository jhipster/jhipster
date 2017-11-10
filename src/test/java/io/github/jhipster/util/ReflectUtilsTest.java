package io.github.jhipster.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

import io.github.jhipster.service.QueryService;

public class ReflectUtilsTest {

    private static IllegalArgumentException exception = new IllegalArgumentException("Eek");

    public ReflectUtilsTest() {
    }

    public int testInstance(int a, int b) {
        return a + b;
    }

    public static int testStatic(int a, int b) {
        return a * b;
    }

    public static void testThrow(String s) {
        throw exception;
    }

    @Test
    public void testLoad() {
        Object test = ReflectUtils.load(getClass().getName());
        assertThat(test).isEqualTo(getClass());
    }

    @Test
    public void testConstruct() {
        Object test = ReflectUtils.construct(getClass().getName());
        assertThat(test).isInstanceOf(getClass());
    }

    @Test
    public void testInstance() {
        Object test = ReflectUtils.invokeInstance(this, "testInstance", 6, 7);
        assertThat(test).isInstanceOf(Integer.class);
        assertThat(test).isEqualTo(13);
    }

    @Test
    public void testStatic() {
        Object test = ReflectUtils.invokeStatic(getClass().getName(), "testStatic", 6, 7);
        assertThat(test).isInstanceOf(Integer.class);
        assertThat(test).isEqualTo(42);
    }

    @Test
    public void testNoSuchClass() {
        Throwable caught = catchThrowable(() -> ReflectUtils.construct("no.such.class"));
        assertThat(caught).isInstanceOf(RuntimeException.class);
        assertThat(caught.getMessage()).startsWith(ReflectUtils.FAILED_CLASS_LOAD_MESSAGE);
        assertThat(caught.getCause()).isInstanceOf(ClassNotFoundException.class);
    }

    @Test
    public void testAbstractClass() {
        Throwable caught = catchThrowable(() -> ReflectUtils.construct(QueryService.class.getName()));
        assertThat(caught).isInstanceOf(RuntimeException.class);
        assertThat(caught.getMessage()).startsWith(ReflectUtils.FAILED_INVOKE_MESSAGE);
        assertThat(caught.getCause()).isInstanceOf(InstantiationException.class);
    }

    @Test
    public void testNoMethod() {
        Throwable caught = catchThrowable(() -> ReflectUtils.construct(getClass().getName(), false));
        assertThat(caught).isInstanceOf(RuntimeException.class);
        assertThat(caught.getMessage()).startsWith(ReflectUtils.FAILED_GET_METHOD_MESSAGE);
        assertThat(caught.getCause()).isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    public void testException() {
        Throwable caught = catchThrowable(() -> ReflectUtils.invokeStatic(getClass().getName(), "testThrow", ""));
        assertThat(caught).isInstanceOf(RuntimeException.class);
        assertThat(caught.getMessage()).startsWith(ReflectUtils.TARGET_EXCEPTION_MESSAGE);
        assertThat(caught.getCause()).isEqualTo(exception);
    }
}
