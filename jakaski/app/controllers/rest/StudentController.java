package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Student;
import models.rest.QueryResponse;
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

    public static void getAllStudents() {
        List<Student> students = Student.findAll();
        List<StudentWrapper> wrappers = new ArrayList<>();
        for (Student student : students) {
            wrappers.add(new StudentWrapper(student));
        }

        renderJSON(new QueryResponse<StudentWrapper>(wrappers));
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
