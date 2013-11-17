package models.rest;

import controllers.rest.Resources;
import models.Instructor;
import models.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang.Validate.notNull;

public class InstructorWrapper {

    private InnerInstructor instructor;

    // Default constructor for Gson
    public InstructorWrapper() {
    }

    public InstructorWrapper(final Instructor instructor) {
        notNull(instructor, "Instructor can't be null");

        this.instructor = new InnerInstructor(
                instructor.getId(),
                instructor.firstname + " " + instructor.surname + " (" + Resources.getInstructorQualification(instructor) + ")",
                Resources.canInstructorSki(instructor),
                Resources.canInstructorSnowboard(instructor),
                getLanguageList(instructor.languages));
    }

    private List<String> getLanguageList(final Set<Lang> languages) {
        notNull(languages, "Languages can't be null");

        List<String> result = new ArrayList<>();
        for (Lang lang : languages) {
            result.add(lang.name);
        }

        return result;
    }

    public static class InnerInstructor {

        private Long id;
        private String name;
        private boolean ski;
        private boolean snowboard;
        private List<String> languages;

        // Default constructor for Gson
        public InnerInstructor() {
        }

        public InnerInstructor(final Long id,
                               final String name,
                               final boolean ski,
                               final boolean snowboard,
                               final List<String> languages) {
            this.id = id;
            this.name = name;
            this.ski = ski;
            this.snowboard = snowboard;
            this.languages = languages;
        }

    }

}
