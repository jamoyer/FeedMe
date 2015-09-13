package rainmanproductions.feedme.restaurants;

import android.support.annotation.NonNull;

import rainmanproductions.feedme.restaurants.dominos.DominosPageFlow;
import rainmanproductions.feedme.restaurants.papajohns.PapaJohnsPageFlow;

public enum Restaurant
{
    DOMINOS(DominosPageFlow.ADDRESS_PAGE, FoodType.PIZZA),
    PAPA_JOHNS(PapaJohnsPageFlow.ADDRESS_PAGE, FoodType.PIZZA);
//    PIZZA_RANCH(null, FoodType.PIZZA),
//    PIZZA_PIT(null, FoodType.PIZZA),
//    PIZZA_HUT(null, FoodType.PIZZA),
//    JEFFS_PIZZA(null, FoodType.PIZZA),
//    JIMMY_JOHNS(null, FoodType.SANDWICH),
//    FIGHTING_BURRITO(null, FoodType.MEXICAN),
//    CHINESE_888(null, FoodType.ASIAN);

    private final RestaurantPageFlow pageFlow;
    private final FoodType foodType;

    Restaurant(final RestaurantPageFlow pageFlow, final FoodType foodType)
    {
        this.pageFlow = pageFlow;
        this.foodType = foodType;
    }

    @NonNull
    public RestaurantPageFlow getRestaurantPageFlow()
    {
        if (pageFlow == null)
        {
            throw new IllegalStateException("PageFlow for " + this.name() + " is null.");
        }
        return pageFlow;
    }

    @NonNull
    public FoodType getFoodType()
    {
        if (foodType == null)
        {
            throw new IllegalStateException("FoodType for " + this.name() + " is null.");
        }
        return foodType;
    }
}