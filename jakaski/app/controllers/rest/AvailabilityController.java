package controllers.rest;

import models.Availability;
import models.Instructor;
import models.Location;
import models.Student;
import models.rest.AvailabilityWrapper;
import models.rest.UriResponse;
import play.mvc.Controller;
import play.mvc.Http;
import controllers.rest.binders.DateSerializer;

public class AvailabilityController extends Controller {

    public static void getAvailability(Long id) {
        Availability availability = Availability.findById(id);
        notFoundIfNull(availability, "Availability does not exist");

        renderJSON(new AvailabilityWrapper(availability), new DateSerializer());
    }

    /*
     * Parameter must be named body in order to work.
     */
    public static void createAvailability(AvailabilityWrapper body) {
        if (body == null) {
            badRequest();
        }

        Instructor instructor = Instructor.findById(body.getInstructorId());
        notFoundIfNull(instructor, "Instructor does not exist");
        Location location = Location.find("byName", body.getLocationName()).first();
        notFoundIfNull(location, "Location does not exist");
        
        Availability availability = body.getAvailability(location, instructor);
        instructor.addAvailability(availability);
        availability.save();

        response.status = Http.StatusCode.CREATED;
        renderJSON(new UriResponse(Resources.getAvailabilityUri(availability)));
    }

    public static void updateAvailability(Long id, AvailabilityWrapper body) {
        if (body == null) {
            badRequest();
        }

        Availability availability = Availability.findById(id);
        notFoundIfNull(availability, "Availability does not exist");  
        Instructor instructor = Instructor.findById(body.getInstructorId());
        notFoundIfNull(instructor, "Instructor does not exist");
        Location location = Location.find("byName", body.getLocationName()).first();
        notFoundIfNull(location, "Location does not exist");

        body.updateAvailability(availability, location).save();
        response.status = Http.StatusCode.NO_RESPONSE;
    }

    public static void deleteAvailability(Long id) {
        Availability availability = Availability.findById(id);
        notFoundIfNull(availability, "Availability does not exist");

        availability.delete();
        response.status = Http.StatusCode.NO_RESPONSE;
    }

}
