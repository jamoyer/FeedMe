package rainmanproductions.feedme.restaurants.papajohns;

import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.restaurants.RestaurantPageFlow;

public enum PapaJohnsPageFlow implements RestaurantPageFlow
{
    ADDRESS_PAGE("https://www.papajohns.com/", "AddressPage.js"),
    DELIVERY_INFO_PAGE("https://www.papajohns.com/order/stores-near-me", "DeliveryInfoPage.js"),
    ORDER_PAGE("https://www.papajohns.com/order/menu", "OrderPage.js"),
    SPECIALS_PAGE("https://www.papajohns.com/order/specials", "Specials.js"),
    CUSTOM_PIZZA_PAGE("www.papajohns.com/order/dealbuilder", "CustomPizza.js"),
    CART_CONFIRMATION_PAGE("https://www.papajohns.com/order/cart-confirmation", "CartConfirmationPage.js"),
    VIEW_CART_PAGE("https://www.papajohns.com/order/view-cart", "ViewCartPage.js"),
    CHECKOUT_PAGE("https://www.papajohns.com/order/checkout", "CheckoutPage.js"),
    DRINK_PAGE("https://www.papajohns.com/order/menu?category=Drinks", "DrinkPage.js");

    private static final String BASE_PATH = "PapaJohns/";
    private static boolean orderedDrink = false;

    private final String url;
    private final String jsFilepath;

    PapaJohnsPageFlow(final String url, final String filename)
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
        return getPapaJohnsPageFromURL(url);
    }

    @Override
    public Restaurant getRestaurantType()
    {
        return Restaurant.PAPA_JOHNS;
    }

    private static PapaJohnsPageFlow getPapaJohnsPageFromURL(final String url)
    {
        if (ADDRESS_PAGE.getURL().equals(url))
        {
            return ADDRESS_PAGE;
        }
        if (DELIVERY_INFO_PAGE.getURL().equals(url))
        {
            return DELIVERY_INFO_PAGE;
        }
        if (ORDER_PAGE.getURL().equals(url))
        {
            return ORDER_PAGE;
        }
        if (VIEW_CART_PAGE.getURL().equals(url))
        {
            return VIEW_CART_PAGE;
        }
        if (CART_CONFIRMATION_PAGE.getURL().equals(url))
        {
            return CART_CONFIRMATION_PAGE;
        }
        if (CHECKOUT_PAGE.getURL().equals(url))
        {
            return CHECKOUT_PAGE;
        }
        if (SPECIALS_PAGE.getURL().equals(url))
        {
            return SPECIALS_PAGE;
        }
        if (url.contains(CUSTOM_PIZZA_PAGE.getURL()))
        {
            return CUSTOM_PIZZA_PAGE;
        }
        if (DRINK_PAGE.getURL().equals(url))
        {
            return DRINK_PAGE;
        }
        throw new IllegalArgumentException("Unrecognized page url: " + url);
    }

}
