package rainmanproductions.feedme.restaurants.dominos;

import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.restaurants.RestaurantPageFlow;

public enum DominosPageFlow implements RestaurantPageFlow
{
    ADDRESS_PAGE("https://www.dominos.com/en/pages/order/#/locations/search/", "AddressPage.js"),
    MENU_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/AllEntrees/", "MenuPage.js"),
    PIZZA_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/Pizza/", "PizzaPage.js"),
    PASTA_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/Pasta/", "PastaPage.js"),
    SANDWICH_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/Sandwich/", "SandwichPage.js"),
    WINGS_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/Wings/", "WingsPage.js"),
    SIDES_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/AllSides/", "SidesPage.js"),
    DRINKS_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/AllDrinks/", "DrinksPage.js"),
    DESSERTS_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/Dessert/", "DessertsPage.js"),
    CHECKOUT_PAGE("https://www.dominos.com/en/pages/order/#/checkout/", "CheckoutPage.js"),
    PAYMENT_PAGE("https://www.dominos.com/en/pages/order/payment.jsp", "PaymentPage.js");

    private static final String BASE_PATH = "Dominos/";
    private static final DominosPageFlow[] VALUES = values();

    private final String url;
    private final String jsFilepath;

    DominosPageFlow(final String url, final String filename)
    {
        this.url = url;
        this.jsFilepath = BASE_PATH + filename;
    }

    @Override
    public String getURL()
    {
        return this.url;
    }

    @Override
    public String getStartingURL()
    {
        return ADDRESS_PAGE.getURL();
    }

    @Override
    public String getJsFilepath()
    {
        return this.jsFilepath;
    }

    @Override
    public RestaurantPageFlow getPageFromURL(String url)
    {
        return getDominosPageFromURL(url);
    }

    @Override
    public Restaurant getRestaurantType()
    {
        return Restaurant.DOMINOS;
    }

    private static DominosPageFlow getDominosPageFromURL(final String url)
    {
        for (final DominosPageFlow pageFlow : VALUES)
        {
            if (pageFlow.getURL().equals(url))
            {
                return pageFlow;
            }
        }
        // This page has been seen before but just redirects to address page
        if ("https://www.dominos.com/en/pages/order/#/product/S_ALFR/builder/".equals(url) ||
            "https://www.dominos.com/en/pages/order/#/product/S_CARB/builder/".equals(url))
        {
            return ADDRESS_PAGE;
        }
        throw new IllegalArgumentException("Unrecognized page url: " + url);
    }
}