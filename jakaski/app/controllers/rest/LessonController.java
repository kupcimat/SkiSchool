package controllers.rest;

import models.Instructor;
import models.Lesson;
import models.LessonType;
import models.Location;
import models.Student;
import models.rest.LessonWrapper;
import models.rest.UriResponse;
import play.mvc.Controller;
import play.mvc.Http;

public class LessonController extends Controller {

    public static void getLesson(Long id) {
        Lesson lesson = Lesson.findById(id);
        notFoundIfNull(lesson, "Lesson does not exist");
        LessonWrapper result = new LessonWrapper(lesson);

        renderJSON(result);
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

        // TODO Temporary static assignment of variables
        Location location = Location.find("byName", "jahodna").first();
        Lesson lesson = body.createLesson(location, LessonType.INDIVIDUAL, instructor, student);
        instructor.addLesson(lesson);
        student.addLesson(lesson);
        lesson.save();

        response.status = Http.StatusCode.CREATED;
        renderJSON(new UriResponse(Resources.getLessonUri(lesson)));
    }

}
