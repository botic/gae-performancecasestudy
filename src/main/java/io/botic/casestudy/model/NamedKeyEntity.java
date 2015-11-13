package io.botic.casestudy.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class NamedKeyEntity {
    @Id
    private String id;
    private String text;

    @Index
    private String indexedIdentifier;

    public NamedKeyEntity() {
    }

    public NamedKeyEntity(String id, String text) {
        this.id = id;
        this.text = text;
        this.indexedIdentifier = id;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIndexedIdentifier() {
        return indexedIdentifier;
    }
}
