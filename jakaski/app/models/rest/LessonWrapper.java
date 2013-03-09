package models.rest;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

import java.util.Date;

import models.Instructor;
import models.Lang;
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
        notNull(lesson, "Lesson can't be null");
        notEmpty(lesson.instructors, "Instructors can't be empty");
        notEmpty(lesson.students, "Students can't be empty");
        notNull(lesson.location, "Location can't be empty");
        notNull(lesson.language, "Language can't be empty");

        Instructor instructor = lesson.instructors.iterator().next();
        Student student = lesson.students.iterator().next();

        this.lesson = new InnerLesson(lesson.getId(), lesson.startTime, lesson.endTime, lesson.note,
                Resources.getInstructorUri(instructor), Resources.getStudentUri(student), lesson.location.name, lesson.language.name,
                lesson.lessonType, lesson.snowboard, lesson.studentsCount, student.fullname);
    }

    public Long getInstructorId() {
        notNull(lesson, "InnerLesson can't be null");
        return Resources.getInstructorId(lesson.instructor);
    }

    public Long getStudentId() {
        notNull(lesson, "InnerLesson can't be null");
        return Resources.getStudentId(lesson.student);
    }

    public String getLocationName() {
        notNull(lesson, "InnerLesson can't be null");
        return lesson.location;
    }

    public String getLanguageName() {
        notNull(lesson, "InnerLesson can't be null");
        return lesson.language;
    }

    public Lesson getLesson(Instructor instructor, Student student, Location location, Lang language) {
        notNull(lesson, "InnerLesson can't be null");
        notNull(instructor, "Instructor can't be null");
        notNull(student, "Student can't be null");
        notNull(location, "Location can't be empty");
        notNull(language, "Language can't be empty");

        Lesson result = new Lesson.Builder()
                .startTime(lesson.start)
                .endTime(lesson.end)
                .note(lesson.note)
                .instructor(instructor)
                .student(student)
                .location(location)
                .language(language)
                .lessonType(lesson.type)
                .snowboard(lesson.snowboard)
                .studentsCount(lesson.count).build();

        return result;
    }

    public Lesson updateLesson(Lesson oldLesson, Instructor instructor, Student student, Location location, Lang language) {
        notNull(lesson, "InnerLesson can't be null");
        notNull(oldLesson, "Lesson can't be null");
        notEmpty(oldLesson.instructors, "Instructors can't be empty");
        notEmpty(oldLesson.students, "Students can't be empty");
        notNull(instructor, "Instructor can't be empty");
        notNull(student, "Student can't be empty");
        notNull(location, "Location can't be empty");
        notNull(language, "Language can't be empty");

        oldLesson.startTime = lesson.start;
        oldLesson.endTime = lesson.end;
        oldLesson.note = lesson.note;

        oldLesson.instructors.clear();
        oldLesson.instructors.add(instructor);
        oldLesson.students.clear();
        oldLesson.students.add(student);

        oldLesson.location = location;
        oldLesson.language = language;
        oldLesson.lessonType = lesson.type;
        oldLesson.snowboard = lesson.snowboard;
        oldLesson.studentsCount = lesson.count;

        return oldLesson;
    }

    public static class InnerLesson {

        private Long id;
        private Date start;
        private Date end;
        private String note;
        private String instructor;
        private String student;
        private String location;
        private String language;
        private LessonType type;
        private boolean snowboard;
        private int count;
        private String studentName;

        // Default constructor for Gson
        public InnerLesson() {
        }

        public InnerLesson(Long id, Date start, Date end, String note, String instructor, String student, String location, String language,
                LessonType type, boolean snowboard, int count, String studentName) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.note = note;
            this.instructor = instructor;
            this.student = student;
            this.location = location;
            this.language = language;
            this.type = type;
            this.snowboard = snowboard;
            this.count = count;
            this.studentName = studentName;
        }

    }

}
