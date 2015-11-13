package io.botic.legacy.objectify.simple;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.Date;

@Entity
public class SingleEntity {
    @Id
    private String id;

    @Parent
    private Ref<SingleEntity> parent;

    private String info;
    private Date created;

    public SingleEntity() {
    }

    public SingleEntity(String id, Ref<SingleEntity> parent, String info) {
        this.id = id;
        this.parent = parent;
        this.info = info;
        this.created = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Ref<SingleEntity> getParent() {
        return parent;
    }

    public void setParent(Ref<SingleEntity> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "SingleEntity{" +
            "id='" + id + '\'' +
            ", parent=" + parent +
            ", info='" + info + '\'' +
            ", created=" + created +
            '}';
    }
}
