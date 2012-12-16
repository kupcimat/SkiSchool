package models.rest;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

import java.util.Date;

import models.Instructor;
import models.Lesson;
import models.LessonType;
import models.Location;
import models.Student;
import controllers.rest.Resources;

public class LessonWrapper {

    private InnerLesson lesson;

    // Default constructor for Gson
    public LessonWrapper() {
    }

    public LessonWrapper(Lesson lesson) {
        notEmpty(lesson.instructors, "Instructors can't be empty");
        notEmpty(lesson.students, "Students can't be empty");

        Instructor instructor = lesson.instructors.iterator().next();
        Student student = lesson.students.iterator().next();

        this.lesson = new InnerLesson(lesson.getId(), lesson.startTime, lesson.endTime, lesson.note,
                Resources.getInstructorUri(instructor), Resources.getStudentUri(student));
    }

    public Long getInstructorId() {
        notNull(lesson, "Lesson can't be null");
        return Resources.getInstructorId(lesson.instructor);
    }

    public Long getStudentId() {
        notNull(lesson, "Lesson can't be null");
        return Resources.getStudentId(lesson.student);
    }

    public Lesson getLesson(Location location, LessonType lessonType, Instructor instructor, Student student) {
        return new Lesson(lesson.start, lesson.end, location, lessonType, lesson.note, instructor, student);
    }

    public Lesson updateLesson(Lesson lesson) {
        lesson.startTime = this.lesson.start;
        lesson.endTime = this.lesson.end;
        lesson.note = this.lesson.note;
        // TODO what about other fields

        return lesson;
    }

    public static class InnerLesson {

        private Long id;
        private Date start;
        private Date end;
        private String note;
        private String instructor;
        private String student;

        // Default constructor for Gson
        public InnerLesson() {
        }

        public InnerLesson(Long id, Date start, Date end, String note, String instructor, String student) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.note = note;
            this.instructor = instructor;
            this.student = student;
        }

    }

}
