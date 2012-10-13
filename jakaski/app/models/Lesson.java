package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Lesson extends Model {

	@Required
	public Date startTime;
	@Required
	public Date endTime;

	// @Required
	// @Pattern(regexp = NamingConstants.LESSON_INDIVIDUAL + "|"
	// + NamingConstants.LESSON_GROUP + "|"
	// + NamingConstants.LESSON_KINDERGARTEN, message = "{lesson_type}")
	// private String lessonType;
	// @Required
	// public boolean snowboard;
	// @Required
	// public boolean executed;
	// @Required
	// public boolean paid;
	public String note;
	@ManyToMany
	public List<Instructor> instructors;
	// @ManyToMany
	// private List<Student> students;
	
	public Lesson(Date startTime, Date endTime, String note) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.note = note;
	}

}
