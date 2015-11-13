package io.botic.casestudy.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The lightweight entity contains just simple data types
 * and has limitation for string lengths. It contains just simple strings.
 */

@Entity
public class LightweightEntity {

    @Id
    private Long id;

    private String strField1;
    private String strField2;
    private String strField3;

    public LightweightEntity() {
    }

    public LightweightEntity(String strField1, String strField2, String strField3) {
        // 350 => primitively ensures every unicode string can be indexed by the Datastore
        if (strField1.length() > 350 || strField2.length() > 350 || strField3.length() > 350) {
            throw new IllegalArgumentException("String length is limited to 350 characters");
        }

        this.strField1 = strField1;
        this.strField2 = strField2;
        this.strField3 = strField3;
    }

    public Long getId() {
        return id;
    }

    public String getStrField1() {
        return strField1;
    }

    public void setStrField1(String strField1) {
        this.strField1 = strField1;
    }

    public String getStrField2() {
        return strField2;
    }

    public void setStrField2(String strField2) {
        this.strField2 = strField2;
    }

    public String getStrField3() {
        return strField3;
    }

    public void setStrField3(String strField3) {
        this.strField3 = strField3;
    }
}
