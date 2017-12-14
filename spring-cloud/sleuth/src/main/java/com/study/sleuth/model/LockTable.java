package com.study.sleuth.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/12/8.
 */
@Entity
@Table(name = "lock_table")
public class LockTable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "guid")
    @GenericGenerator(name = "guid", strategy = "guid")
    private String id;
    private String keytype;

    private String value;



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }
}
