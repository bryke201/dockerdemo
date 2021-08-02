package com.dockersample.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "test_item")
public class TestItem {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "value")
    private String value;

    // used by JPA
    protected TestItem() {
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    // it is a good practice to include the ff in "entities":


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestItem testItem = (TestItem) o;
        return id.equals(testItem.id) &&
                value.equals(testItem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "TestItem{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
