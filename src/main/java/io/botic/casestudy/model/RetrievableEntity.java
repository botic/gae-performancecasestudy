package io.botic.casestudy.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * This is a special entity created to run read tests.
 * It can handle some payload in the unindexed string field.
 */
@Entity
public class RetrievableEntity {
    @Id
    private Long id;

    @Index private String name;
    @Index private int number;
    @Index private Date date;

    private String longStringField;

    public RetrievableEntity() {
    }

    public RetrievableEntity(String name, int number, Date date, String longStringField) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.longStringField = longStringField;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLongStringField() {
        return longStringField;
    }

    public void setLongStringField(String longStringField) {
        this.longStringField = longStringField;
    }

    @Override
    public String toString() {
        return "RetrievableEntity{" +
            "name='" + name + '\'' +
            ", number=" + number +
            ", date=" + date +
            '}';
    }
}
