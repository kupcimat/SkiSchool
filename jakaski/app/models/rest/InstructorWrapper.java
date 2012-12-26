package models.rest;

import models.Instructor;

public class InstructorWrapper {

    private InnerInstructor instructor;

    // Default constructor for Gson
    public InstructorWrapper() {
    }

    public InstructorWrapper(Instructor instructor) {
        String name = instructor.firstname + " " + instructor.surname;
        this.instructor = new InnerInstructor(instructor.getId(), name);
    }

    public static class InnerInstructor {

        private Long id;
        private String name;

        // Default constructor for Gson
        public InnerInstructor() {
        }

        public InnerInstructor(Long id, String name) {
            this.id = id;
            this.name = name;
        }

    }

}
