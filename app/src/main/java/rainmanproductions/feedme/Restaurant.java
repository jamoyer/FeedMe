package rainmanproductions.feedme;

import rainmanproductions.feedme.dominos.DominosPageFlow;
import rainmanproductions.feedme.papajohns.PapaJohnsPageFlow;

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
