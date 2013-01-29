package models.rest;

import static org.apache.commons.lang.Validate.notNull;
import models.Student;

public class StudentWrapper {

    private InnerUser student;

    // Default constructor for Gson
    public StudentWrapper() {
    }

    public StudentWrapper(Student student) {
        this.student = new InnerUser(student.getId(), student.fullname);
    }

    public Student getStudent() {
        notNull(student, "InnerStudent can't be null");
        return new Student(student.getName(), "", "");
    }

}
