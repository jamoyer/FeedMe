package rainmanproductions.feedme.restaurants;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

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
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        Boolean excludeDominos = Boolean.parseBoolean(accessor.getInfo(InfoType.PREFERENCE_DOMINOS));
        Boolean exclude888 = Boolean.parseBoolean(accessor.getInfo(InfoType.PREFERENCE_888_CHINESE));
        Boolean excludePapaJohns = Boolean.parseBoolean(accessor.getInfo(InfoType.PREFERENCE_PAPA_JOHNS));
        Boolean excludeJimmyJohns = Boolean.parseBoolean(accessor.getInfo(InfoType.PREFERENCE_JIMMY_JOHNS));

        /*Only choose restaurants that are allowed*/
        List<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RESTAURANTS)
        {
            boolean exclude = false;
            switch (restaurant)
            {
                case DOMINOS:
                    exclude = excludeDominos;
                    break;
                case PAPA_JOHNS:
                    exclude = excludePapaJohns;
                    break;
            }
            if (!exclude)
            {
                restaurants.add(restaurant);
            }
        }
        int randomIndex = RANDOM_INSTANCE.nextInt(restaurants.size());
        return restaurants.get(randomIndex);
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