package rainmanproductions.feedme.restaurants;

import rainmanproductions.feedme.restaurants.dominos.DominosPageFlow;
import rainmanproductions.feedme.restaurants.papajohns.PapaJohnsPageFlow;

public enum Restaurant
{
    DOMINOS(DominosPageFlow.ADDRESS_PAGE),
    PAPA_JOHNS(PapaJohnsPageFlow.ADDRESS_PAGE),
    JIMMY_JOHNS(null);

    private final RestaurantPageFlow pageFlow;

    Restaurant(final RestaurantPageFlow pageFlow)
    {
        this.pageFlow = pageFlow;
    }

    public RestaurantPageFlow getRestaurantPageFlow()
    {
        return pageFlow;
    }
}
