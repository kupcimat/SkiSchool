package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Availability extends Model {
	@Required
	public Date startTime;
	@Required
	public Date endTime;
	public String note;
	@ManyToOne
	public Instructor instructor;

	public Availability(Date startTime, Date endTime, String note, Instructor instructor) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.note = note;
		this.instructor = instructor;
	}

}
