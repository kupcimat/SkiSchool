package controllers;

import java.util.Date;

import models.Availability;
import models.Instructor;
import models.Location;

public class SchedulerEvent {

    public String start_date;
    public String end_date;
    public String text;

    public SchedulerEvent(String startDate, String endDate, String text) {
        this.start_date = startDate;
        this.end_date = endDate;
        this.text = text;
    }

    public Availability createAvailability(Location location, Instructor instructor) {
        return new Availability(new Date(), new Date(), location, text, instructor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SchedulerEvent [start_date=");
        builder.append(start_date);
        builder.append(", end_date=");
        builder.append(end_date);
        builder.append(", text=");
        builder.append(text);
        builder.append("]");

        return builder.toString();
    }

}
