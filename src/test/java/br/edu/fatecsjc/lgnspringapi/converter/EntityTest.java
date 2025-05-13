package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.Objects;

public class EntityTest {
    private String value;

    public EntityTest() {
    }

    public EntityTest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // equals e hashCode para facilitar as comparações nos testes.
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EntityTest))
            return false;
        EntityTest that = (EntityTest) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
