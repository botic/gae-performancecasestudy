package io.botic.legacy.objectify.eg;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class EGTeam {
    @Id
    private Long id;

    private String name;
    private EGAddress location;
    private Long budget;

    public EGTeam() {
    }

    public EGTeam(String name, EGAddress location, Long budget) {
        this.name = name;
        this.location = location;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EGAddress getLocation() {
        return location;
    }

    public void setLocation(EGAddress location) {
        this.location = location;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }
}
