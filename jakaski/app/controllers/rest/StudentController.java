package controllers.rest;

import models.Student;
import models.rest.StudentWrapper;
import models.rest.UriResponse;
import play.mvc.Controller;
import play.mvc.Http;

public class StudentController extends Controller {

    public static void getStudent(Long id) {
        Student student = Student.findById(id);
        notFoundIfNull(student, "Student does not exist");

        renderJSON(new StudentWrapper(student));
    }

    public static void createStudent(StudentWrapper body) {
        if (body == null) {
            badRequest();
        }

        Student student = body.getStudent();
        student.save();

        response.status = Http.StatusCode.CREATED;
        renderJSON(new UriResponse(Resources.getStudentUri(student)));
    }

}
