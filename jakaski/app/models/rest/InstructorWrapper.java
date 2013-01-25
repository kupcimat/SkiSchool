package models.rest;

import models.Instructor;

public class InstructorWrapper {

    private InnerUser instructor;

    // Default constructor for Gson
    public InstructorWrapper() {
    }

    public InstructorWrapper(Instructor instructor) {
        String name = instructor.firstname + " " + instructor.surname;
        this.instructor = new InnerUser(instructor.getId(), name);
    }

}
