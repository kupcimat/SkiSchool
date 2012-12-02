package models.rest;

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
        // TODO notEmpty(instructors)
        // TODO notEmpty(students)
        Instructor instructor = lesson.instructors.iterator().next();
        Student student = lesson.students.iterator().next();

        this.lesson = new InnerLesson(lesson.startTime, lesson.endTime, lesson.note, Resources.getInstructorUri(instructor),
                Resources.getStudentUri(student));
    }

    public Long getInstructorId() {
        // TODO notNull(lesson)
        return Resources.getInstructorId(lesson.instructor);
    }

    public Long getStudentId() {
        // TODO notNull(lesson)
        return Resources.getStudentId(lesson.student);
    }

    public Lesson createLesson(Location location, LessonType lessonType, Instructor instructor, Student student) {
        return new Lesson(lesson.start, lesson.end, location, lessonType, lesson.note, instructor, student);
    }

    public static class InnerLesson {

        private Date start;
        private Date end;
        private String note;
        private String instructor;
        private String student;

        // Default constructor for Gson
        public InnerLesson() {
        }

        public InnerLesson(Date start, Date end, String note, String instructor, String student) {
            this.start = start;
            this.end = end;
            this.note = note;
            this.instructor = instructor;
            this.student = student;
        }

    }

}
