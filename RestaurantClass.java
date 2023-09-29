package cchatela_CSCI201_Assignment2;

import java.util.List;

public class RestaurantClass {

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private List<String> menu;
    private int drivers;

    // Source: Looked at Prof. Papa's post: https://piazza.com/class/lkyma5x3edl626/post/43
    // However, did not generate any code. Typed it all out myself

    public RestaurantClass(String newRestaurantName, String address2, double lat, double lon, List<String> menuItems, int drive) {
        this.name = newRestaurantName;
        this.address = address2;
        this.latitude = lat;
        this.longitude = lon;
        this.menu = menuItems;
        this.drivers = drive;
    }

    /* getters */
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<String> getMenu() {
        return menu;
    }

    public int getDrivers() {
        return drivers;
    }

    /* setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public void setDrivers(int drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='"+name+'\''+
                ", address='"+address+'\''+
                ", latitude="+latitude+
                ", longitude="+longitude+
                ", menu="+menu+'}';
    }
}