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

        if (lesson == null) {
            notFound();
        }
        LessonWrapper result = new LessonWrapper(lesson);

        renderJSON(result);
    }

    /*
     * Parameter must be named body in order to work.
     */
    public static void createLesson(LessonWrapper body) {
        Instructor instructor = Instructor.findById(body.getInstructorId());
        Student student = Student.findById(body.getStudentId());

        if (instructor == null || student == null) {
            notFound("Instructor or student does not exist");
        }
        // TODO Temporary static assignement of variables
        Lesson lesson = body.createLesson(Location.JAHODNA, LessonType.INDIVIDUAL, instructor, student);
        instructor.addLesson(lesson);
        student.addLesson(lesson);
        lesson.save();

        response.status = Http.StatusCode.CREATED;
        renderJSON(new UriResponse(Resources.getLessonUri(lesson)));
    }

}
