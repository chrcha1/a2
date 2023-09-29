package cchatela_CSCI201_Assignment2;

import java.util.List;


public class RestaurantsWrapper {
    private List<RestaurantClass> data;

    public RestaurantsWrapper(List<RestaurantClass> data) {
        this.data = data;
    }

    public List<RestaurantClass> getData() {
        return data;
    }

    public void setData(List<RestaurantClass> data) {
        this.data = data;
    }
}
