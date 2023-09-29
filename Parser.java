package cchatela_CSCI201_Assignment1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Parser {

    public static List<RestaurantClass> parseFile(String filename) {
        Gson gson = new Gson();
        File file = new File(filename);
        try (FileReader fileReader = new FileReader(file)) {
            RestaurantsWrapper data = gson.fromJson(fileReader, RestaurantsWrapper.class);
            if (data == null || data.getData() == null) {
                System.out.println("The file does not contain any valid data.");
                return null;
            }

            boolean invalidData = false;
            for (RestaurantClass restaurant : data.getData()) {
                if (restaurant.getName() == null || restaurant.getAddress() == null
                        || restaurant.getLatitude() == null || restaurant.getLongitude() == null
                        || restaurant.getMenu() == null || restaurant.getMenu().isEmpty()) {
                    invalidData = true;
                    break;
                }
            }

            if (invalidData) {
                System.out.println("The file " + filename + " has missing parameters.");
                return null;
            }
            return data.getData();
        } catch (IOException e) {
            System.out.println("The file " + filename + " could not be found.");
        } catch (JsonSyntaxException e) {
            System.out.println("The file " + filename + " is not formatted properly.");
        }
        return null;
    }
}