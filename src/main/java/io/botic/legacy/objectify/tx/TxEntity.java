package io.botic.legacy.objectify.tx;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class TxEntity {
    @Id
    private String id;

    @Parent
    private Ref<TxEntity> parent;

    @Index
    private String text;

    public TxEntity() {
    }

    public TxEntity(String id, Ref<TxEntity> parent, String text) {
        this.id = id;
        this.parent = parent;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ref<TxEntity> getParent() {
        return parent;
    }

    public void setParent(Ref<TxEntity> parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {

        return "TxEntity{" +
            "id=" + id +
            ", parent=" + (parent != null ? parent.getKey().getName() : null) +
            ", text='" + text + '\'' +
            '}';
    }
}
