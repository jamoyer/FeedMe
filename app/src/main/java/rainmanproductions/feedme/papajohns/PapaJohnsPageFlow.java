package rainmanproductions.feedme.papajohns;

/**
 * Created by Matt on 9/9/2015.
 */
public enum PapaJohnsPageFlow {
    ADDRESS_PAGE("https://www.papajohns.com/", "AddressPage.js"),
    DELIVERY_INFO_PAGE("https://www.papajohns.com/order/stores-near-me", "DeliveryInfoPage.js"),
    ORDER_PAGE("https://www.papajohns.com/order/menu", "OrderPage.js"),
    CART_CONFIRMATION_PAGE("https://www.papajohns.com/order/cart-confirmation", "CartConfirmationPage.js"),
    VIEW_CART_PAGE("https://www.papajohns.com/order/view-cart", "ViewCartPage.js"),
    CHECKOUT_PAGE("https://www.papajohns.com/order/checkout", "CheckoutPage.js");

    private static final String BASE_PATH = "PapaJohns/";
    private static boolean orderedDrink = false;

    private final String url;
    private final String jsFilepath;

    PapaJohnsPageFlow(final String url, final String filename)
    {
        this.url = url;
        this.jsFilepath = BASE_PATH + filename;
    }

    public String getURL()
    {
        return this.url;
    }

    public String getJsFilepath()
    {
        return this.jsFilepath;
    }

    public static PapaJohnsPageFlow getPageFromURL(final String url)
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
        throw new IllegalArgumentException("Unrecognized page url: " + url + " Substring was " + (url+"padding to test for substring so I don't have to write an if statement and there are always at least 50 chars yo").substring(0,50));
    }

    public static String getStartingURL()
    {
        return ADDRESS_PAGE.getURL();
    }

}
