package rainmanproductions.feedme.dominos;

public enum DominosPageFlow
{
    ADDRESS_PAGE("https://www.dominos.com/en/pages/order/#/locations/search/", "AddressPage.js"),
    MENU_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/AllEntrees/", "MenuPage.js"),
    CHECKOUT_PAGE("https://www.dominos.com/en/pages/order/#/checkout/", "CheckoutPage.js"),
    PAYMENT_PAGE("https://www.dominos.com/en/pages/order/payment.jsp", "PaymentPage.js");

    private static final String BASE_PATH = "Dominos/";

    private final String url;
    private final String jsFilepath;

    DominosPageFlow(final String url, final String filename)
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

    public static DominosPageFlow getPageFromURL(final String url)
    {
        if (ADDRESS_PAGE.getURL().equals(url))
        {
            return ADDRESS_PAGE;
        }
        if (MENU_PAGE.getURL().equals(url))
        {
            return MENU_PAGE;
        }
        if (CHECKOUT_PAGE.getURL().equals(url))
        {
            return CHECKOUT_PAGE;
        }
        if (PAYMENT_PAGE.getURL().equals(url))
        {
            return PAYMENT_PAGE;
        }
        throw new IllegalArgumentException("Unrecognized page url: " + url);
    }

    public static String getStartingURL()
    {
        return ADDRESS_PAGE.getURL();
    }
}