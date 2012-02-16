package org.cvut.skischool.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.cvut.skischool.core.DisplayableInterface;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.core.NumberConstants;

/**
 *
 * @author matej
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Lesson.getAllLessons",
    query = "SELECT l FROM Lesson l ORDER BY l.startTime DESC"),
    @NamedQuery(name = "Lesson.getLessonsByDate",
    query = "SELECT l FROM Lesson l JOIN l.instructors i WHERE (i = :instructor) AND (l.startTime BETWEEN :dayBegin AND :dayEnd) ORDER BY l.startTime"),
    @NamedQuery(name = "Lesson.getLessonsByInstructorAndTime",
    query = "SELECT l FROM Lesson l JOIN l.instructors i WHERE (i = :instructor) AND ((:startTime BETWEEN l.startTime AND l.endTime) OR (:endTime BETWEEN l.startTime AND l.endTime) OR (l.startTime BETWEEN :startTime AND :endTime))"),
    @NamedQuery(name = "Lesson.countLessonsLangBonus",
    query = "SELECT COUNT(l) FROM Lesson l JOIN l.instructors i WHERE (i = :instructor) AND (l.language IS NOT NULL) AND (l.startTime BETWEEN :startDate AND :endDate)"),
    @NamedQuery(name = "Lesson.countLessons",
    query = "SELECT l FROM Lesson l JOIN l.instructors i WHERE (i = :instructor) AND (l.lessonType = :type) AND (l.startTime BETWEEN :startDate AND :endDate)"),
    @NamedQuery(name = "Lesson.getLessonsByInstructorAndInterval",
    query = "SELECT l FROM Lesson l JOIN l.instructors i WHERE (i = :instructor) AND (l.startTime BETWEEN :startDate AND :endDate) ORDER BY l.startTime DESC"),
    @NamedQuery(name = "Lesson.getLessonsByStudentAndInterval",
    query = "SELECT l FROM Lesson l JOIN l.students i WHERE (i = :student) AND (l.startTime BETWEEN :startDate AND :endDate) ORDER BY l.startTime DESC"),
    @NamedQuery(name = "Lesson.getAllLessonsByType",
    query = "SELECT l FROM Lesson l WHERE (l.lessonType = :type) ORDER BY l.startTime DESC")})
public class Lesson implements Serializable, DisplayableInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "{null}")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;
    @NotNull(message = "{null}")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;
    @NotNull(message = "{null}")
    @Pattern(regexp = NamingConstants.LESSON_INDIVIDUAL + "|" + NamingConstants.LESSON_GROUP + "|" + NamingConstants.LESSON_KINDERGARTEN, message = "{lesson_type}")
    private String lessonType;
    @NotNull
    private boolean snowboard;
    @NotNull
    private boolean executed;
    @NotNull
    private boolean paid;
    private String note;
    @Size(min = 0, message = "{lesson_instructors}")
    @ManyToMany
    private List<Instructor> instructors;
    @Size(min = 0, message = "{lesson_students}")
    @ManyToMany
    private List<Student> students;
    @ManyToMany
    private List<Price> prices;
    @ManyToOne
    private Lang language;
    @ManyToOne
    private Payment payment;
    @ManyToOne
    private Salary salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isSnowboard() {
        return snowboard;
    }

    public void setSnowboard(boolean snowboard) {
        this.snowboard = snowboard;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Lang getLanguage() {
        return language;
    }

    public void setLanguage(Lang language) {
        this.language = language;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @Override
    public Date startTime() {
        return startTime;
    }

    @Override
    public Date endTime() {
        return endTime;
    }

    @Override
    public String title() {
        return note;
    }

    @Override
    public String color() {
        if (lessonType == null) {
            return NumberConstants.COLOR_LESSON_INDIVIDUAL;
        }
        if (lessonType.compareTo(NamingConstants.LESSON_INDIVIDUAL) == 0) {
            return NumberConstants.COLOR_LESSON_INDIVIDUAL;
        }
        if (lessonType.compareTo(NamingConstants.LESSON_KINDERGARTEN) == 0) {
            return NumberConstants.COLOR_LESSON_KINDERGARTEN;
        }

        return NumberConstants.COLOR_LESSON_INDIVIDUAL;
    }

    @Override
    public boolean isAvailability() {
        return false;
    }

    @Override
    public boolean isLesson() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lesson)) {
            return false;
        }
        Lesson other = (Lesson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cvut.x33eja.model.Lesson[id=" + id + "]";
    }
}
