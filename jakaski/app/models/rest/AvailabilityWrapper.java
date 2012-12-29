package models.rest;

import static org.apache.commons.lang.Validate.notNull;

import java.util.Date;

import models.Availability;
import models.Instructor;
import models.Location;
import controllers.rest.Resources;

public class AvailabilityWrapper {

    private InnerAvailability availability;

    // Default constructor for Gson
    public AvailabilityWrapper() {
    }

    public AvailabilityWrapper(Availability a) {
        notNull(a, "Availability can't be null");
        this.availability = new InnerAvailability(a.getId(), a.startTime, a.endTime, a.note, Resources.getInstructorUri(a.instructor));
    }

    public Long getInstructorId() {
        notNull(availability, "InnerAvailability can't be null");
        return Resources.getInstructorId(availability.instructor);
    }

    public Availability getAvailability(Location location, Instructor instructor) {
        notNull(availability, "InnerAvailability can't be null");
        return new Availability(availability.start, availability.end, location, availability.note, instructor);
    }

    public Availability updateAvailability(Availability availability) {
        notNull(availability, "Availability can't be null");
        notNull(this.availability, "InnerAvailability can't be null");

        availability.startTime = this.availability.start;
        availability.endTime = this.availability.end;
        availability.note = this.availability.note;

        return availability;
    }

    public static class InnerAvailability {

        private Long id;
        private Date start;
        private Date end;
        private String note;
        private String instructor;

        // Default constructor for Gson
        public InnerAvailability() {
        }

        public InnerAvailability(Long id, Date start, Date end, String note, String instructor) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.note = note;
            this.instructor = instructor;
        }

    }

}
