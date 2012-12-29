package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Availability extends Model {

    @Required
    public Date startTime;
    @Required
    public Date endTime;
    @ManyToOne
    public Location location;
    public String note;
    @ManyToOne
    public Instructor instructor;

    public Availability(Date startTime, Date endTime, Location location, String note, Instructor instructor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.note = note;
        this.instructor = instructor;
    }

}
