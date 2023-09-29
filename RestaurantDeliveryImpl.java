package cchatela_CSCI201_Assignment1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class RestaurantsImpl {
    public static void main(String[] args) {
        List<RestaurantClass> restaurants = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {

            String filename = "";
            while (true) {
                System.out.print("What is the name of the restaurant file? ");
                filename = scanner.nextLine();
                restaurants = Parser.parseFile(filename);
                if (restaurants != null) {
                    System.out.println("The file has been properly read.");
                    break; // we can exit the loop now that it's been successfully read
                }
                System.out.println();
            }



            // asking for latitude
            Double latitude = null;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("What is your latitude? ");
                try {
                    latitude = scanner.nextDouble();
                    if (latitude >= -90 && latitude <= 90) {
                        validInput = true;
                    } else {
                        System.out.println("Latitude must be between -90 and 90.");
                        System.out.println();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number for latitude.");
                    System.out.println();
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            System.out.println();

            // asking for longitude
            Double longitude = null;
            validInput = false;
            while (!validInput) {
                System.out.print("What is your longitude? ");
                try {
                    longitude = scanner.nextDouble();
                    if (longitude >= -180 && longitude <= 180) {
                        validInput = true;
                    } else {
                        System.out.println("Longitude must be between -180 and 180.");
                        System.out.println();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number for longitude.");
                    System.out.println();
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            System.out.println();


            while (true) {
                System.out.println("\t1) Display all restaurants");
                System.out.println("\t2) Search for a restaurant");
                System.out.println("\t3) Search for a menu item");
                System.out.println("\t4) Add a new restaurant");
                System.out.println("\t5) Remove a restaurant");
                System.out.println("\t6) Sort restaurants");
                System.out.println("\t7) Exit");
                System.out.print("What would you like to do? ");

                int choice = 0;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    System.out.println();
                    continue;

                }
                System.out.println();
                switch (choice) {

                    case 1: // displaying all restaurants
                        for (RestaurantClass restaurant : restaurants) {
                            // Stack Overflow. (2016, October 12). How do I round to the nearest ten? (1 line) Retrieved from https://stackoverflow.com/questions/39824914/how-do-i-round-to-the-nearest-ten
                            Double distance =calculateDistance(latitude, longitude,
                                    restaurant.getLatitude(), restaurant.getLongitude());
                            System.out.println(restaurant.getName() + ", located " + Math.round(distance * 10) / 10.0 + " miles away at " + restaurant.getAddress());
                        }
                        System.out.println();
                        break;

                    case 2: // searching for a restaurant
                        System.out.print("What is the name of the restaurant you would like to search for? ");
                        String searchName = scanner.nextLine().trim();
                        System.out.println();
                        boolean found = false;
                        for (RestaurantClass restaurant : restaurants) {
                            if (restaurant.getName().toLowerCase().contains(searchName.toLowerCase())) {
                                found = true;
                                double distance = calculateDistance(latitude, longitude,
                                        restaurant.getLatitude(),restaurant.getLongitude());
                                System.out.println(restaurant.getName() + ", located " + Math.round(distance * 10) / 10.0 + " miles away at " + restaurant.getAddress());
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println(searchName + " could not be found.");
                        }
                        System.out.println();
                        break;

                    case 3: // searching for a menu item
                        System.out.print("What menu item would you like to search for? ");
                        String menuItem = scanner.nextLine().trim();
                        System.out.println();
                        boolean itemFound = false;

                        for (RestaurantClass restaurant : restaurants) {
                            List<String> matchingItems = new ArrayList<>();

                            for (String item : restaurant.getMenu()) {
                                if (item.toLowerCase().contains(menuItem.toLowerCase())) {
                                    matchingItems.add(item);
                                    itemFound = true;
                                }
                            }

                            if (!matchingItems.isEmpty()) {
                                System.out.print(restaurant.getName() + " serves ");
                                for (int i = 0; i < matchingItems.size(); i++) {
                                    if (i > 0 && i == matchingItems.size() - 1) {
                                        System.out.print(" and ");
                                    } else if (i > 0) {
                                        System.out.print(", ");
                                    }
                                    System.out.print(matchingItems.get(i));
                                }
                                System.out.println(".");
                            }
                        }

                        if (!itemFound) {
                            System.out.println("No restaurant nearby serves " + menuItem + ".");
                        }
                        System.out.println();
                        break;

                    case 4: // adding new restaurant
                        while(true) {
                            System.out.print("What is the name of the restaurant you would like to add? ");
                            String newRestaurantName = scanner.nextLine().trim();
                            System.out.println();
                            boolean exists = false;
                            for (RestaurantClass restaurant :restaurants){
                                if (restaurant.getName().equalsIgnoreCase(newRestaurantName)) {
                                    System.out.println("There is already an entry for " + newRestaurantName + ".");
                                    System.out.println();
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
                                System.out.print("What is the address for " + newRestaurantName + "? ");
                                String address = scanner.nextLine().trim();
                                System.out.println();

                                double lat;
                                while (true) {
                                    System.out.print("What is the latitude for " + newRestaurantName + "? ");
                                    try {
                                        lat = Double.parseDouble(scanner.nextLine().trim());
                                        break; // exit the loop if the input is a valid double
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a numerical value for latitude.");
                                    }
                                }
                                System.out.println();

                                double lon;
                                while (true) {
                                    System.out.print("What is the longitude for " + newRestaurantName + "? ");
                                    try {
                                        lon = Double.parseDouble(scanner.nextLine().trim());
                                        break; // exit the loop if the input is a valid double
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a numerical value for longitude.");
                                    }
                                }
                                System.out.println();
                                List<String> menuItems = new ArrayList<>();
                                while (true) {
                                    System.out.print("What does " + newRestaurantName + " serve? ");
                                    menuItems.add(scanner.nextLine().trim());
                                    System.out.println();

                                    String choice_;
                                    while (true) {
                                        System.out.println("\n1) Yes\n2) No");
                                        System.out.print("Does " + newRestaurantName + " serve anything else? ");
                                        choice_ = scanner.nextLine().trim();
                                        if (choice_.equals("1") || choice_.equals("2")) {
                                            break;
                                        } else {
                                            System.out.println("Please enter a valid choice (1 or 2).");
                                        }
                                    }

                                    if (choice_.equals("2")) {
                                        break;
                                    }
                                    System.out.println();
                                }
                                System.out.println();
                                RestaurantClass newRestaurant = new RestaurantClass(newRestaurantName, address, lat, lon, menuItems);
                                restaurants.add(newRestaurant);

                                double distance = calculateDistance(latitude, longitude, lat, lon);
                                System.out.println("There is now a new entry for:");
                                double roundedDistance = Math.round(distance * 10) / 10.0;
                                System.out.println(newRestaurant.getName() + ", located " + roundedDistance + " miles away at " + newRestaurant.getAddress());
                                System.out.print(newRestaurant.getName() + " serves ");
                                for (int i = 0; i < menuItems.size(); i++) {
                                    if (i > 0 && i == menuItems.size() - 1) {
                                        System.out.print(" and ");
                                    } else if (i > 0) {
                                        System.out.print(", ");
                                    }
                                    System.out.print(menuItems.get(i));
                                }
                                System.out.println(".");

                                break;
                            }
                        }
                        System.out.println();
                        break;

                    case 5:
                        System.out.println("Which restaurant would you like to remove?");
                        for (int i = 0; i < restaurants.size(); i++) {
                            System.out.println((i + 1) + ") " + restaurants.get(i).getName());
                        }

                        int choiceToRemove = scanner.nextInt();
                        scanner.nextLine();

                        if (choiceToRemove > 0 && choiceToRemove <= restaurants.size()) {
                            System.out.println(restaurants.get(choiceToRemove - 1).getName() + " is now removed.");
                            System.out.println();
                            restaurants.remove(choiceToRemove - 1);
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;

                    case 6:
                        System.out.println("What would you like to do?\n");
                        System.out.println("\t1) A to Z");
                        System.out.println("\t2) Z to A");
                        System.out.println("\t3) Closest to farthest");
                        System.out.println("\t4) Farthest to closest");
                        System.out.print("How would you like to sort by? ");


                        int choice6;
                        while (true) {
                            try {
                                choice6 = Integer.parseInt(scanner.nextLine());
                                if (choice6 >= 1 && choice6 <= 4) {
                                    break;
                                } else {
                                    System.out.println("Please enter a valid option (1-4).");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a number.");
                            }
                        }
                        System.out.println();

                        switch (choice6) {
                            //  “How can I sort a Java list of Restaurant objects by various attributes such as name, and distance" prompt (10 lines). ChatGPT, 4 Sep. version, OpenAI, 6 Sep. 2023, chat.openai.com/chat.
                            case 1:
                                Collections.sort(restaurants, Comparator.comparing(RestaurantClass::getName));
                                System.out.println("Your restaurants are now sorted A to Z.");
                                System.out.println();
                                break;
                            case 2:
                                Collections.sort(restaurants, Comparator.comparing(RestaurantClass::getName).reversed());
                                System.out.println("Your restaurants are now sorted Z to A.");
                                System.out.println();
                                break;
                            case 3:
                                Double finalLatitude = latitude;
                                Double finalLongitude = longitude;
                                Collections.sort(restaurants, (r1, r2) -> Double.compare(
                                        calculateDistance(finalLatitude, finalLongitude, r1.getLatitude(), r1.getLongitude()),
                                        calculateDistance(finalLatitude, finalLongitude, r2.getLatitude(), r2.getLongitude())
                                ));
                                System.out.println("Your restaurants are now sorted from closest to farthest.");
                                System.out.println();
                                break;
                            case 4:
                                Double finalLatitude1 = latitude;
                                Double finalLongitude1 = longitude;
                                Collections.sort(restaurants, (r1, r2) -> Double.compare(
                                        calculateDistance(finalLatitude1, finalLongitude1, r2.getLatitude(), r2.getLongitude()),
                                        calculateDistance(finalLatitude1, finalLongitude1, r1.getLatitude(), r1.getLongitude())
                                ));
                                System.out.println("Your restaurants are now sorted from farthest to closest.");
                                System.out.println();
                                break;
                        }
                        break;


                    case 7:

                        while (true) {
                            System.out.println("\n1) Yes\n2) No");
                            System.out.print("Would you like to save your edits? ");
                            System.out.println();
                            String choice7 = scanner.nextLine().trim();

                            if ("1".equals(choice7)) {
                                saveToFile(restaurants, filename);
                                System.out.println("Your edits have been saved to " + filename + ".\nThank you for using my program!");
                                break;
                            }
                            else if ("2".equals(choice7)) {
                                System.out.println("Your edits have not been saved.");
                                break;
                            }
                            else {
                                System.out.println("That is not a valid selection. Please select either 1 or 2.");

                            }
                        }
                        return;
                    default:
                        System.out.println("That is not a valid option.\n");
                }

            }
        }
        catch (JsonIOException e) {
            e.printStackTrace();
        }

    }

    public static void saveToFile(List<RestaurantClass> restaurants, String filename) {
        // "GSON - Object Serialization." prompt (1-2 lines), TutorialsPoint, https://www.tutorialspoint.com/gson/gson_serialization_examples.htm.

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(new RestaurantsWrapper(restaurants));

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error saving data to " + filename);
        }
    }

    public static double calculateDistance(Double currLat, Double currLong, double restLat, double restLong) {
        currLat = Math.toRadians(currLat);
        currLong = Math.toRadians(currLong);
        restLat = Math.toRadians(restLat);
        restLong = Math.toRadians(restLong);

        double distance = 3963.0 * Math.acos(
                (Math.sin(currLat) * Math.sin(restLat))
                        + Math.cos(currLat) * Math.cos(restLat) * Math.cos(restLong - currLong)
        );

        return distance;
    }
}
