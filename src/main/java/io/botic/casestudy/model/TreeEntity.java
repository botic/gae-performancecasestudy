package io.botic.casestudy.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class TreeEntity {
    @Id
    private String id;
    private String text;

    @Parent
    private Ref<TreeEntity> parent;

    public TreeEntity() {
    }

    public TreeEntity(String id, String text, Ref<TreeEntity> parent) {
        this.id = id;
        this.text = text;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public Ref<TreeEntity> getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
