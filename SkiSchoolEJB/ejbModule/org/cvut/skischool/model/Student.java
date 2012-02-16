package org.cvut.skischool.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author matej
 */
@Entity
@DiscriminatorValue(value = "student")
@NamedQueries({
    @NamedQuery(name = "Student.getAllStudents",
    query = "SELECT s FROM Student s ORDER BY s.lastName"),
    @NamedQuery(name = "Student.getStudentsByName",
    query = "SELECT s FROM Student s WHERE (UPPER (s.firstName) LIKE :name) OR (UPPER (s.lastName) LIKE :name)")})
public class Student extends Person implements Serializable {

    private int age;
    @NotNull
    private boolean ski;
    @NotNull
    private boolean snowboard;
    @NotNull
    private int groupSize;
    private String note;
    @ManyToMany(mappedBy = "students")
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "student")
    private Set<Payment> payments;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isSki() {
        return ski;
    }

    public void setSki(boolean ski) {
        this.ski = ski;
    }

    public boolean isSnowboard() {
        return snowboard;
    }

    public void setSnowboard(boolean snowboard) {
        this.snowboard = snowboard;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
