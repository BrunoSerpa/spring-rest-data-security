package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.Objects;

public class DTOTest {
    private String value;

    public DTOTest() {
    }

    public DTOTest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DTOTest))
            return false;
        DTOTest DTOTest = (DTOTest) o;
        return Objects.equals(getValue(), DTOTest.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}