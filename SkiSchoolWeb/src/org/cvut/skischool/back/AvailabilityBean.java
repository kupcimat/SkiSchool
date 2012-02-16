package org.cvut.skischool.back;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.cvut.skischool.beans.AvailabilityManagementLocal;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.core.DateTools;
import org.cvut.skischool.core.NumberConstants;
import org.cvut.skischool.model.Availability;
import org.cvut.skischool.model.Instructor;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author sabolmi1
 */
@ManagedBean(name = "availabilityBean")
@SessionScoped
public class AvailabilityBean implements Serializable {

    @EJB
    private AvailabilityManagementLocal availabilityManagement;
    @EJB
    private UserManagementLocal userManagement;
    private Availability availability;
    private DataModel<Availability> availabilities;
    private Instructor instructor;
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;
    private Date date;
    private int interval;

    public AvailabilityBean() {
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;

    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public DataModel<Availability> getAllAvailabilities() {
        availabilities = new ListDataModel<Availability>(availabilityManagement.getAvailabilityByDate(instructor, date));
        return availabilities;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void handleDateSelect(DateSelectEvent event) {
        date = event.getDate();
    }

    public String newAvailability() {
        availability = new Availability();
        return "availabilities?faces-redirect=true";
    }

    public void setWholeDay(ActionEvent event) {
        startHour = NumberConstants.DAY_START_HOUR;
        startMinutes = NumberConstants.DAY_START_MINUTES;
        endHour = NumberConstants.DAY_END_HOUR;
        endMinutes = NumberConstants.DAY_END_MINUTES;
    }

    public void createAvailabilityInterval(Date startDate, int interval) {
        Date nextDay = date;
        for (int i = 0; i < interval + 1; i++) {
            nextDay = DateTools.addDays(date, i);

            availability.setStartTime(DateTools.makeDateTime(nextDay, startHour, startMinutes));
            availability.setEndTime(DateTools.makeDateTime(nextDay, endHour, endMinutes));
            instructor.getAvailability().add(availability);
            userManagement.updateInstructor(instructor);
        }
    }

    public String editAvailability() {
        availability = availabilities.getRowData();
        return "availabislities?faces-redirect=true";
    }

    public void saveAvailability(ActionEvent event) {
        Date time1 = DateTools.makeDateTime(date, startHour, startMinutes);
        Date time2 = DateTools.makeDateTime(date, endHour, endMinutes);
        if (time2.before(time1)) {
//            showErrorMessage("End time is before start time");
             showErrorMessage("Koniec je pred začiatkom");
            return;
        }
        if (time2.equals(time1)) {
//            showErrorMessage("Start time and end time are equal");
             showErrorMessage("Začiatok a koniec sú rovnaké");
            return;
        }
        if (availabilityManagement.getConflictAvailabilities(instructor, time1, time2).size() > 0) {
//            showErrorMessage("Conflict availability");
            showErrorMessage("Dostupnosť koliduje s inou dostupnosťou");
            return;
        }
        availability.setInstructor(instructor);
        createAvailabilityInterval(date, interval);
//        showInfoMessage("Availability created");
        showInfoMessage("Dostupnosť vytvorená");
    }

    public void deleteAvailability(ActionEvent event) {
        availabilityManagement.deleteAvailability(availabilities.getRowData());
//        showWarnMessage("Availability deleted");
        showWarnMessage("Dostupnosť zmazaná");
    }

    private void showInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showWarnMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
