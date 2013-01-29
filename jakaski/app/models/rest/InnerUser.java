package models.rest;

public class InnerUser {

    private Long id;
    private String name;

    // Default constructor for Gson
    public InnerUser() {
    }

    public InnerUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
