package io.botic.casestudy.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * The heavy entity contains a lot of datatypes and text.
 * It also incorporates a lot of indexes
 */

@Entity
public class HeavyweightEntity {

    @Id
    private Long id;

    private String longStringField1;
    private String longStringField2;
    private String longStringField3;
    private String longStringField4;
    private String longStringField5;
    @Index private String shortStringField;
    @Index private int intField;
    @Index private long longField;
    @Index private float floatField;
    @Index private double doubleField;
    @Index private boolean booleanField;
    @Index private byte byteField;
    @Index private Date dateField;
    @Index private byte[] byteArrayField;


    public HeavyweightEntity() {
    }

    public HeavyweightEntity(String longStringField1, String longStringField2, String longStringField3, String longStringField4, String longStringField5, String shortStringField, int intField, long longField, float floatField, double doubleField, boolean booleanField, byte byteField, Date dateField, byte[] byteArrayField) {
        this.longStringField1 = longStringField1;
        this.longStringField2 = longStringField2;
        this.longStringField3 = longStringField3;
        this.longStringField4 = longStringField4;
        this.longStringField5 = longStringField5;
        this.shortStringField = shortStringField;
        this.intField = intField;
        this.longField = longField;
        this.floatField = floatField;
        this.doubleField = doubleField;
        this.booleanField = booleanField;
        this.byteField = byteField;
        this.dateField = dateField;
        this.byteArrayField = byteArrayField;
    }

    public Long getId() {
        return id;
    }

    public String getLongStringField1() {
        return longStringField1;
    }

    public void setLongStringField1(String longStringField1) {
        this.longStringField1 = longStringField1;
    }

    public String getLongStringField2() {
        return longStringField2;
    }

    public void setLongStringField2(String longStringField2) {
        this.longStringField2 = longStringField2;
    }

    public String getLongStringField3() {
        return longStringField3;
    }

    public void setLongStringField3(String longStringField3) {
        this.longStringField3 = longStringField3;
    }

    public String getLongStringField4() {
        return longStringField4;
    }

    public void setLongStringField4(String longStringField4) {
        this.longStringField4 = longStringField4;
    }

    public String getLongStringField5() {
        return longStringField5;
    }

    public void setLongStringField5(String longStringField5) {
        this.longStringField5 = longStringField5;
    }

    public String getShortStringField() {
        return shortStringField;
    }

    public void setShortStringField(String shortStringField) {
        this.shortStringField = shortStringField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public float getFloatField() {
        return floatField;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public byte getByteField() {
        return byteField;
    }

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public byte[] getByteArrayField() {
        return byteArrayField;
    }

    public void setByteArrayField(byte[] byteArrayField) {
        this.byteArrayField = byteArrayField;
    }
}
