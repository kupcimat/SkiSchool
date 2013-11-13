package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Payment extends Model {
	
	@Required
    public Date timestamp;
	@ManyToOne
    public Student student;
	
	public int individualLessonCount;
	public int groupLessonCount;
	public int kindergartenLessonCount;
    
	/**
     * Real paid amount
     */
	public int sum;

	public Payment(Date timestamp, Student student, int individualLessonCount, int groupLessonCount, int kindergartenLessonCount, int sum) {
	    this.timestamp = timestamp;
	    this.student = student;
	    this.individualLessonCount = individualLessonCount;
	    this.groupLessonCount = groupLessonCount;
	    this.kindergartenLessonCount = kindergartenLessonCount;
	    this.sum = sum;
    }
	
}
