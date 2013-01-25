package models.rest;

import models.Student;

public class StudentWrapper {

    private InnerUser student;

    // Default constructor for Gson
    public StudentWrapper() {
    }

    public StudentWrapper(Student student) {
        this.student = new InnerUser(student.getId(), student.fullname);
    }

}
