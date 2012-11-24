package controllers.scheduler;

import java.util.Date;

import models.Availability;
import models.Instructor;
import models.Location;

public class SchedulerEvent {

    public Date start_date;
    public Date end_date;
    public String text;

    public SchedulerEvent(Date startDate, Date endDate, String text) {
        this.start_date = startDate;
        this.end_date = endDate;
        this.text = text;
    }

    public Availability createAvailability(Location location, Instructor instructor) {
        return new Availability(start_date, end_date, location, text, instructor);
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

    public static class Response {
        public long id;

        public Response(long id) {
            this.id = id;
        }
    }

}
