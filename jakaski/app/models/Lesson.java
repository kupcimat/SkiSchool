package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Lesson extends Model {

    @Required
    public Date startTime;
    @Required
    public Date endTime;
    @ManyToMany
    public Set<Instructor> instructors;
    @ManyToMany
    public Set<Student> students;
    @ManyToOne
    public Location location;
    @ManyToOne
    public Lang language;

    public String note;
    public LessonType lessonType;
    public boolean snowboard;
    public int studentsCount;

    public Lesson(Date startTime, Date endTime, String note, Set<Instructor> instructors, Set<Student> students, Location location,
            Lang language, LessonType lessonType, boolean snowboard, int studentsCount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.instructors = instructors;
        this.students = students;
        this.location = location;
        this.language = language;
        this.lessonType = lessonType;
        this.snowboard = snowboard;
        this.studentsCount = studentsCount;
    }

    public static class Builder {

        private Date startTime;
        private Date endTime;
        private String note;
        private Set<Instructor> instructors;
        private Set<Student> students;
        private Location location;
        private Lang language;
        private LessonType lessonType;
        private boolean snowboard;
        private int studentsCount;

        public Builder() {
            instructors = new HashSet<>();
            students = new HashSet<>();
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructors.add(instructor);
            return this;
        }

        public Builder student(Student student) {
            this.students.add(student);
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder language(Lang language) {
            this.language = language;
            return this;
        }

        public Builder lessonType(LessonType lessonType) {
            this.lessonType = lessonType;
            return this;
        }

        public Builder snowboard(boolean snowboard) {
            this.snowboard = snowboard;
            return this;
        }

        public Builder studentsCount(int studentsCount) {
            this.studentsCount = studentsCount;
            return this;
        }

        public Lesson build() {
            return new Lesson(startTime, endTime, note, instructors, students, location, language, lessonType, snowboard, studentsCount);
        }

    }

}
