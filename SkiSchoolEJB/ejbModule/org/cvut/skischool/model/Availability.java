package org.cvut.skischool.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.cvut.skischool.core.DisplayableInterface;
import org.cvut.skischool.core.NumberConstants;

/**
 *
 * @author matej
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Availability.getAvailableInstructorsByDate",
    query = "SELECT DISTINCT a.instructor FROM Availability a WHERE a.startTime BETWEEN :dayBegin AND :dayEnd"),
    @NamedQuery(name = "Availability.getAvailableInstructorsByTime",
    query = "SELECT DISTINCT a.instructor FROM Availability a WHERE (:timeBegin BETWEEN a.startTime AND a.endTime)"),
    @NamedQuery(name = "Availability.getAvailabilityByDate",
    query = "SELECT a FROM Availability a WHERE (a.instructor = :instructor) AND (a.startTime BETWEEN :dayBegin AND :dayEnd) ORDER BY a.startTime"),
    @NamedQuery(name = "Availability.getConflictAvailabilities",
    query = "SELECT a FROM Availability a WHERE (a.instructor = :instructor) AND ((:startTime BETWEEN a.startTime AND a.endTime) OR (:endTime BETWEEN a.startTime AND a.endTime) OR (a.startTime BETWEEN :startTime AND :endTime))"),
    @NamedQuery(name = "Availability.getAvailabilityByTimeAndInstructor",
    query = "SELECT a FROM Availability a WHERE (a.instructor = :instructor) AND (:startTime BETWEEN a.startTime AND a.endTime) AND (:endTime BETWEEN a.startTime AND a.endTime)")})
public class Availability implements Serializable, DisplayableInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;
    private String note;
    @ManyToOne
    private Instructor instructor;

    public Availability() {
    }

    public Availability(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public Date startTime() {
        return startTime;
    }

    @Override
    public Date endTime() {
        return endTime;
    }

    @Override
    public String title() {
        return note;
    }

    @Override
    public String color() {
        return NumberConstants.COLOR_AVAILABILITY;
    }

    @Override
    public boolean isAvailability() {
        return true;
    }

    @Override
    public boolean isLesson() {
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Availability)) {
            return false;
        }
        Availability other = (Availability) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cvut.x33eja.model.Availability[id=" + id + "]";
    }
}
