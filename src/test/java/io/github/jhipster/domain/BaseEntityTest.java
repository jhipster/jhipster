package io.github.jhipster.domain;

import org.junit.Test;

import com.google.common.base.MoreObjects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.persistence.Column;
import javax.persistence.Entity;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Henri Tremblay
 */
public class BaseEntityTest {

    @Entity
    public static class A extends BaseEntity {

        @Column(length = 100)
        private String login;

        public A(Long id, String login) {
            super(id);
            this.login = login;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        @Override
        protected MoreObjects.ToStringHelper toStringHelper() {
            return super.toStringHelper()
                .add("login", login);
        }
    }

    private final A a = new A(2L, "test");

    @Test
    public void getId() {
        assertThat(a.getId()).isEqualTo(2L);
    }

    @Test
    public void setId() {
        a.setId(3L);
        assertThat(a.getId()).isEqualTo(3L);
    }

    @Test
    public void serializable() throws Exception {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try(ObjectOutputStream out = new ObjectOutputStream(bOut)) {
            out.writeObject(a);
        }

        try(ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bOut.toByteArray()))) {
            A actual = (A) in.readObject();
            assertThat(actual.getId()).isEqualTo(2L);
            assertThat(actual.getLogin()).isEqualTo("test");
        }
    }

    @Test
    public void testEquals_Same() {
        assertThat(a).isEqualTo(a);
    }

    @Test
    public void testEquals_SameId() {
        A b = new A(a.getId(), a.getLogin());
        assertThat(a).isEqualTo(b);
    }

    @Test
    public void testEquals_NullId() {
        a.setId(null);
        assertThat(a).isEqualTo(a);

        A b = new A(a.getId(), a.getLogin());
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    public void testEquals_DifferentId() {
        A b = new A(a.getId(), a.getLogin());
        b.setId(3L);
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    public void testEquals_DifferentInstance() {
        A b = new A(a.getId(), a.getLogin()) {};
        assertThat(a).isNotEqualTo(b);

        BaseEntity c = new BaseEntity(a.getId()) {};
        assertThat(a).isNotEqualTo(c);
    }

    @Test
    public void testHashCode() {
        A b = new A(a.getId(), a.getLogin());
        assertThat(b.hashCode()).isEqualTo(a.hashCode());
    }

    @Test
    public void testToString() {
        assertThat(a.toString()).isEqualTo("A{id=2, login=test}");
    }
}
