package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Instructor;
import models.rest.InstructorWrapper;
import models.rest.QueryResponse;
import play.mvc.Controller;

public class InstructorController extends Controller {

    public static void getInstructor(Long id) {
        Instructor instructor = Instructor.findById(id);
        notFoundIfNull(instructor, "Instructor does not exist");

        renderJSON(new InstructorWrapper(instructor));
    }

    public static void getAllInstructors() {
        List<Instructor> instructors = Instructor.findAll();
        List<InstructorWrapper> wrappers = new ArrayList<>();
        for (Instructor instructor : instructors) {
            wrappers.add(new InstructorWrapper(instructor));
        }

        renderJSON(new QueryResponse<InstructorWrapper>(wrappers));
    }

}
