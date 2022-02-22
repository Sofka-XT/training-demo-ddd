package co.sideralis.core.queries.dojo.model;

public class Location {

    private String urlMeet;
    private String location;
    private String description;
    private LocationProp openingHours;

    public String getUrlMeet() {
        return urlMeet;
    }

    public void setUrlMeet(String urlMeet) {
        this.urlMeet = urlMeet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationProp getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocationProp openingHours) {
        this.openingHours = openingHours;
    }

}
