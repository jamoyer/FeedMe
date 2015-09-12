package rainmanproductions.feedme.restaurants;

import rainmanproductions.feedme.restaurants.dominos.DominosPageFlow;
import rainmanproductions.feedme.restaurants.papajohns.PapaJohnsPageFlow;

public enum Restaurant
{
    Dominos(DominosPageFlow.ADDRESS_PAGE),
    PapaJohns(PapaJohnsPageFlow.ADDRESS_PAGE),
    JimmyJohns(null);

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
