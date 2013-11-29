package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Student extends Model {

	@Unique
    @Required
    @MinSize(2)
    public String fullname;
    @Email
    public String email;
    @Phone
    public String phone;

    @ManyToMany(mappedBy = "students")
    public Set<Lesson> lessons;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    public Set<Payment> payments;

    /**
     * Absolute number of paid Lessons
     */
    public int paidLessons;

    public Student(String fullname, String email, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
    
    @Override
  	public String toString() {
  		return fullname;
  	}

}
