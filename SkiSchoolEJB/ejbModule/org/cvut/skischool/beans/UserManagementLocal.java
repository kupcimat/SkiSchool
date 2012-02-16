package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Student;
import org.cvut.skischool.model.Instructor;

/**
 *
 * @author sabolmi1
 */
@Local
public interface UserManagementLocal {

    void createStudent(Student student);

    void createInstructor(Instructor instructor);

    void updateStudent(Student student);

    void updateInstructor(Instructor instructor);

    void deleteStudent(Student student);

    void deleteInstructor(Instructor instructor);

    List<Student> getAllStudents();

    List<Student> getStudentsByName(String name);

    List<Instructor> getAllInstructors();

    List<Instructor> getInstructorsByName(String name);

    Student getStudent(long id);

    Instructor getInstructor(long id);
}
