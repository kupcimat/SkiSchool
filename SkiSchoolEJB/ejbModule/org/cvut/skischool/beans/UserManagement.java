package org.cvut.skischool.beans;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.mail.search.SearchException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Student;

/**
 *
 * @author sabolmi1
 */
@Stateless
public class UserManagement implements UserManagementLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = (List<Student>) em.createNamedQuery("Student.getAllStudents").getResultList();
        return students;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        List<Student> students = (List<Student>) em.createNamedQuery("Student.getStudentsByName")
                .setParameter("name", "%" + name.toUpperCase().replace(" ", "") + "%")
                .getResultList();
        return students;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = (List<Instructor>) em.createNamedQuery("Instructor.getAllInstructors").getResultList();
        return instructors;
    }

    @Override
    public List<Instructor> getInstructorsByName(String name) {
        List<Instructor> instructors = (List<Instructor>) em.createNamedQuery("Instructor.getInstructorsByName")
                .setParameter("name", "%" + name.toUpperCase().replace(" ","") + "%")
                .getResultList();
        return instructors;
    }

    @Override
    public Student getStudent(long id) {
        Student student = em.find(Student.class, id);
        return student;
    }

    @Override
    public Instructor getInstructor(long id) {
        Instructor instructor = em.find(Instructor.class, id);
        return instructor;
    }

    @Override
    @RolesAllowed({"administrator"})
    public void createStudent(Student student) {
        em.persist(student);
    }

    @Override
    @RolesAllowed({"administrator"})
    public void createInstructor(Instructor instructor) {
        em.persist(instructor);
    }

    @Override
    @RolesAllowed({"administrator"})
    public void deleteStudent(Student student) {
        // if is in history update disabled
        //...
        //else
        em.remove(em.merge(student));
    }

    @Override
    @RolesAllowed({"administrator"})
    public void deleteInstructor(Instructor instructor) {
        // if is in history update disabled
        //...
        //else
        em.remove(em.merge(instructor));
    }

    @Override
    @RolesAllowed({"administrator"})
    public void updateStudent(Student student) {
        em.merge(student);
    }

    @Override
    public void updateInstructor(Instructor instructor) {
        em.merge(instructor);
    }
}
