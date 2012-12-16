package controllers.rest;

import play.db.jpa.Model;

public class Resources {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";

    private static final String INSTRUCTOR = "/instructor/";
    private static final String STUDENT = "/student/";
    private static final String LESSON = "/lesson/";

    public static String getInstructorUri(Model object) {
        return getUri(INSTRUCTOR, object);
    }

    public static String getStudentUri(Model object) {
        return getUri(STUDENT, object);
    }

    public static String getLessonUri(Model object) {
        return getUri(LESSON, object);
    }

    public static Long getInstructorId(String uri) {
        return getId(INSTRUCTOR, uri);
    }

    public static Long getStudentId(String uri) {
        return getId(STUDENT, uri);
    }

    private static String getUri(String prefix, Model object) {
        return prefix + object.getId().toString();
    }

    private static Long getId(String prefix, String uri) {
        return Long.valueOf(uri.replaceAll(prefix, ""));
    }

}
