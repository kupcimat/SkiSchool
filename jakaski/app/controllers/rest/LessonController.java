package controllers.rest;

import models.Instructor;
import models.Lang;
import models.Lesson;
import models.Location;
import models.Student;
import models.rest.LessonWrapper;
import models.rest.UriResponse;
import play.mvc.Controller;
import play.mvc.Http;
import controllers.rest.binders.DateSerializer;

public class LessonController extends Controller {

    public static void getLesson(Long id) {
        Lesson lesson = Lesson.findById(id);
        notFoundIfNull(lesson, "Lesson does not exist");

        renderJSON(new LessonWrapper(lesson), new DateSerializer());
    }

    /*
     * Parameter must be named body in order to work.
     */
    public static void createLesson(LessonWrapper body) {
        if (body == null) {
            badRequest();
        }

        Instructor instructor = Instructor.findById(body.getInstructorId());
        notFoundIfNull(instructor, "Instructor does not exist");
        Student student = Student.findById(body.getStudentId());
        notFoundIfNull(student, "Student does not exist");

        Location location = Location.find("byName", body.getLocationName()).first();
        notFoundIfNull(location, "Location does not exist");
        Lang language = Lang.find("byName", body.getLanguageName()).first();
        notFoundIfNull(language, "Language does not exist");

        Lesson lesson = body.getLesson(instructor, student, location, language);
        instructor.addLesson(lesson);
        student.addLesson(lesson);
        lesson.save();

        response.status = Http.StatusCode.CREATED;
        renderJSON(new UriResponse(Resources.getLessonUri(lesson)));
    }

    public static void updateLesson(Long id, LessonWrapper body) {
        if (body == null) {
            badRequest();
        }

        Lesson lesson = Lesson.findById(id);
        notFoundIfNull(lesson, "Lesson does not exist");
        Instructor instructor = Instructor.findById(body.getInstructorId());
        notFoundIfNull(instructor, "Instructor does not exist");
        Student student = Student.findById(body.getStudentId());
        notFoundIfNull(student, "Student does not exist");

        Location location = Location.find("byName", body.getLocationName()).first();
        notFoundIfNull(location, "Location does not exist");
        Lang language = Lang.find("byName", body.getLanguageName()).first();
        notFoundIfNull(language, "Language does not exist");

        body.updateLesson(lesson, instructor, student, location, language).save();
        response.status = Http.StatusCode.NO_RESPONSE;
    }

    public static void deleteLesson(Long id) {
        Lesson lesson = Lesson.findById(id);
        notFoundIfNull(lesson, "Lesson does not exist");

        lesson.delete();
        response.status = Http.StatusCode.NO_RESPONSE;
    }

}
