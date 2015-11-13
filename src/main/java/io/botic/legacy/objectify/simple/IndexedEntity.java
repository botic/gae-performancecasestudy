package io.botic.legacy.objectify.simple;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
@Index
public class IndexedEntity {
    @Id
    private Long id;

    private String stringField;
    private int intField;
    private long longField;
    private float floatField;
    private double doubleField;

    private boolean booleanField;
    private byte byteField;

    private Date dateField;

    private byte[] byteArrayField;

    public IndexedEntity() {
    }

    public IndexedEntity(String stringField, int intField, long longField, float floatField, double doubleField, boolean booleanField, byte byteField, Date dateField, byte[] byteArrayField) {
        this.stringField = stringField;
        this.intField = intField;
        this.longField = longField;
        this.floatField = floatField;
        this.doubleField = doubleField;
        this.booleanField = booleanField;
        this.byteField = byteField;
        this.dateField = dateField;
        this.byteArrayField = byteArrayField;
    }
}
