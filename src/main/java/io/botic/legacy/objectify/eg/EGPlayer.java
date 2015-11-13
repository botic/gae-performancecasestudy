package io.botic.legacy.objectify.eg;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.Date;

@Entity
public class EGPlayer implements Comparable {
    @Id
    private Long id;

    @Parent
    private Ref<EGTeam> team;

    private String firstName;
    private String lastName;
    private Date birthday;
    private int shirtNumber;

    public EGPlayer() {
    }

    public EGPlayer(String firstName, String lastName, Date birthday, int shirtNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.shirtNumber = shirtNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Ref<EGTeam> getTeam() {
        return team;
    }

    public void setTeam(Ref<EGTeam> team) {
        this.team = team;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof EGPlayer) {
            if (this.shirtNumber == ((EGPlayer) o).shirtNumber) {
                return 0;
            } else if (this.shirtNumber < ((EGPlayer) o).shirtNumber) {
                return -1;
            } else {
                return 1;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", team=" + team +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthday=" + birthday +
            ", shirtNumber=" + shirtNumber +
            '}';
    }
}
