package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Instructor extends User {

    public String positionSki;
    public String positionSnowboard;
    // @Enumerated(EnumType.STRING)
    // public Lang languages;

    @ManyToMany(mappedBy = "instructors")
    public Set<Lesson> lessons;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    public Set<Availability> availabilities;

    public Instructor(String email, String password, String firstname, String surname, String phone) {
        super(email, password, firstname, surname, phone);
        this.availabilities = new HashSet<Availability>();
        this.lessons = new HashSet<Lesson>();
    }

    public Instructor addAvailability(Date startTime, Date endTime, Location location, String note) {
        Availability availability = new Availability(startTime, endTime, location, note, this).save();
        this.availabilities.add(availability);
        this.save();
        return this;
    }

    public Instructor addAvailability(Availability availability) {
        availability.save();
        this.availabilities.add(availability);
        this.save();
        return this;
    }

    public static Instructor getByEmail(String userName) {
        return find("byEmail", userName).first();
    }
}
