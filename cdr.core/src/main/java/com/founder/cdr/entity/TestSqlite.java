package com.founder.cdr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述
 * Created by masong1 on 2016/9/6.
 */

@Entity
public class TestSqlite  implements Serializable {
    private BigDecimal id;
    private String name;
    private Integer age;
    private String address;

    @Column(name = "ANESTHESIA_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age", nullable = false, precision = 11, scale = 0)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "address", length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
