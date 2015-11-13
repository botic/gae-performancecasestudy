package io.botic.legacy.objectify.flat;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class FlatTeam {
    @Id
    private Long id;

    private String name;
    private FlatAddress location;
    private Long budget;

    private SortedSet<Ref<FlatPlayer>> players = new TreeSet<Ref<FlatPlayer>>();

    public FlatTeam() {
    }

    public FlatTeam(String name, FlatAddress location, Long budget) {
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

    public FlatAddress getLocation() {
        return location;
    }

    public void setLocation(FlatAddress location) {
        this.location = location;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public SortedSet<Ref<FlatPlayer>> getPlayers() {
        return players;
    }

    public void setPlayers(SortedSet<Ref<FlatPlayer>> players) {
        this.players = players;
    }
}
