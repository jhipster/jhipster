package io.github.jhipster.config.cache;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>PrefixedSimpleKey class.</p>
 */
public class PrefixedSimpleKey implements Serializable {

    private final String prefix;
    private final Object[] params;
    private final String methodName;
    private int hashCode;

    /**
     * <p>Constructor for PrefixedSimpleKey.</p>
     *
     * @param prefix a {@link java.lang.String} object.
     * @param methodName a {@link java.lang.String} object.
     * @param elements a {@link java.lang.Object} object.
     */
    public PrefixedSimpleKey(String prefix, String methodName, Object... elements) {
        Assert.notNull(prefix, "Prefix must not be null");
        Assert.notNull(elements, "Elements must not be null");
        this.prefix = prefix;
        this.methodName = methodName;
        this.params = new Object[elements.length];
        System.arraycopy(elements, 0, this.params, 0, elements.length);
        this.hashCode = prefix.hashCode();
        this.hashCode = 31 * this.hashCode + methodName.hashCode();
        this.hashCode = 31 * this.hashCode + Arrays.deepHashCode(this.params);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return (this == other ||
            (other instanceof PrefixedSimpleKey && this.prefix.equals(((PrefixedSimpleKey) other).prefix) &&
                this.methodName.equals(((PrefixedSimpleKey) other).methodName) &&
                Arrays.deepEquals(this.params, ((PrefixedSimpleKey) other).params)));
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.prefix + " " + getClass().getSimpleName() + this.methodName + " [" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
    }
}
