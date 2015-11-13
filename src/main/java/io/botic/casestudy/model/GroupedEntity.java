package io.botic.casestudy.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

/**
 * An entity that should be used for entity group building.
 */
@Entity
public class GroupedEntity {
    @Id
    private Long id;

    @Index
    private String title;

    @Index
    private int level;

    @Parent
    private Ref<GroupedEntity> parent;

    public GroupedEntity() {
    }

    public GroupedEntity(String title, int level) {
        this.title = title;
        this.level = level;
    }

    public GroupedEntity(String title, GroupedEntity parent, int level) {
        this.title = title;
        this.parent = Ref.create(parent);
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Ref<GroupedEntity> getParent() {
        return parent;
    }

    public void setParent(Ref<GroupedEntity> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "GroupedEntity <id=" + id +
            ", parent=" + (parent != null ? parent.getKey().getId() : null) +
            ", title='" + title + "'" +
            ">";
    }
}
