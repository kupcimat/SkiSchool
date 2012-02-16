package org.cvut.skischool.model;

import java.io.Serializable;
import java.util.List;
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
@DiscriminatorValue(value = "instructor")
@NamedQueries({
    @NamedQuery(name = "Instructor.getAllInstructors",
    query = "SELECT i FROM Instructor i ORDER BY i.lastName"),
    @NamedQuery(name = "Instructor.getInstructorsByName",
    query = "SELECT i FROM Instructor i WHERE (UPPER (i.firstName) LIKE :name) OR (UPPER (i.lastName) LIKE :name)")})
public class Instructor extends Person implements Serializable {

    @NotNull(message = "{null}")
    private String sex;
    private String bankAccount;
    @NotNull(message = "{null}")
    private String positionSki;
    @NotNull(message = "{null}")
    private String positionSnowboard;
    @NotNull(message = "{null}")
    private int seasonHours;
    @NotNull(message = "{null}")
    private int totalHours;
    @NotNull(message = "{null}")
    private boolean active;
    private String note;
    @OneToMany(mappedBy = "instructor")
    private List<Availability> availability;
    @ManyToMany(mappedBy = "instructors")
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "instructor")
    private Set<Salary> salaries;
    @ManyToMany
    private Set<Lang> languages;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPositionSki() {
        return positionSki;
    }

    public void setPositionSki(String positionSki) {
        this.positionSki = positionSki;
    }

    public String getPositionSnowboard() {
        return positionSnowboard;
    }

    public void setPositionSnowboard(String positionSnowboard) {
        this.positionSnowboard = positionSnowboard;
    }

    public int getSeasonHours() {
        return seasonHours;
    }

    public void setSeasonHours(int seasonHours) {
        this.seasonHours = seasonHours;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<Salary> salaries) {
        this.salaries = salaries;
    }

    public Set<Lang> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Lang> languages) {
        this.languages = languages;
    }
}
