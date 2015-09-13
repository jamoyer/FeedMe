package rainmanproductions.feedme.restaurants;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodPicker
{
    private static final Random RANDOM_INSTANCE = new Random();
    private static final FoodType[] FOOD_TYPES = FoodType.values();
    private static final Restaurant[] RESTAURANTS = Restaurant.values();

    private FoodPicker()
    {
    }

    public static FoodType getUniformRandomFoodType()
    {
        int randomIndex = RANDOM_INSTANCE.nextInt(FOOD_TYPES.length);
        return FOOD_TYPES[randomIndex];
    }

    public static Restaurant getUniformRandomRestaurant()
    {
        int randomIndex = RANDOM_INSTANCE.nextInt(RESTAURANTS.length);
        return RESTAURANTS[randomIndex];
    }

    public static Restaurant getUniformRandomRestaurantByFoodType(final FoodType foodType)
    {
        if (foodType == null)
        {
            throw new IllegalArgumentException("FoodType cannot be null.");
        }

        // gather all restaurants of this food type
        List<Restaurant> validRestaurants = new ArrayList<>();
        for (Restaurant restaurant : RESTAURANTS)
        {
            if (restaurant.getFoodType() == foodType)
            {
                validRestaurants.add(restaurant);
            }
        }

        if (validRestaurants.isEmpty())
        {
            throw new IllegalStateException("No restaurants for the given food type: " + foodType.name());
        }

        int randomIndex = RANDOM_INSTANCE.nextInt(validRestaurants.size());
        return validRestaurants.get(randomIndex);
    }
}