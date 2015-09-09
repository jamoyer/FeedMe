package rainmanproductions.feedme.dominos;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DominosWebViewClient extends WebViewClient
{

    private enum DominosPageFlow
    {
        ADDRESS_PAGE("https://www.dominos.com/en/pages/order/#/locations/search/"),
        MENU_PAGE("https://www.dominos.com/en/pages/order/#/section/Food/category/AllEntrees/"),
        CHECKOUT_PAGE("https://www.dominos.com/en/pages/order/#/checkout/"),
        PAYMENT_PAGE("https://www.dominos.com/en/pages/order/payment.jsp");

        private final String url;

        DominosPageFlow(final String url)
        {
            this.url = url;
        }

        public String getURL()
        {
            return this.url;
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
    }

    public static String getStartingURL()
    {
        return DominosPageFlow.ADDRESS_PAGE.getURL();
    }

    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        System.out.println("Entering Dominos on page finished. url=" + url);
        DominosPageFlow page = DominosPageFlow.getPageFromURL(url);
        switch (page)
        {
            case ADDRESS_PAGE:
            {
                doAddressPage(view);
                break;
            }
            case MENU_PAGE:
            {
                doMenuPage(view);
                break;
            }
            case CHECKOUT_PAGE:
            {
                doCheckoutPage(view);
                break;
            }
            case PAYMENT_PAGE:
            {
                doPaymentPage(view);
                break;
            }
            default:
            {
                System.err.println("Unexpected url: " + url);
            }
        }
        System.out.println("Leaving Dominos on page finished.");
    }

    private void doAddressPage(final WebView view)
    {
        System.out.println("Entering doAddressPage()");
        String page = "javascript:\n" +
                "/* wait for document to be ready and loaded before doing anything */\n" +
                "while (document.readyState !== 'complete');\n" +
                "function pageInteractor() \n" +
                "{\n" +
                "    var serviceElements = document.getElementsByName('Service_Type');\n" +
                "    for (var i=0; i<serviceElements.length; i++) \n" +
                "    {\n" +
                "        if (serviceElements[i].value === 'Delivery')\n" +
                "        {\n" +
                "            serviceElements[i].click();\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "    var addressSelect = document.getElementById('Address_Type_Select');\n" +
                "    for (var i=0; i<addressSelect.options.length; i++) \n" +
                "    {\n" +
                "        if (addressSelect.options[i].value === 'Apartment')\n" +
                "        {\n" +
                "            addressSelect.selectedIndex = i;\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "    var regionSelect = document.getElementById('Region');\n" +
                "    for (var i=0; i<regionSelect.options.length; i++) \n" +
                "    {\n" +
                "        if (regionSelect.options[i].value === 'IA') \n" +
                "        {\n" +
                "            regionSelect.selectedIndex = i;\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "    document.getElementById('Street').value = '150 Campus Ave';\n" +
                "    document.getElementById('Address_Line_2').value = '22';\n" +
                "    document.getElementById('City').value = 'Ames';\n" +
                "    document.getElementById('Postal_Code').value = '50014';\n" +
                "    document.evaluate( '/html/body//form//button[@type=\\\"submit\\\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();\n" +
                "}\n" +
                "pageInteractor();";
        view.loadUrl(page);
        System.out.println("Leaving doAddressPage()");
    }

    private void doMenuPage(final WebView view)
    {
        System.out.println("Entering doMenuPage()");
        String page = "javascript:\n" +
                "/* wait for document to be ready and loaded before doing anything */\n" +
                "while (document.readyState !== 'complete');\n" +
                "function pageInteractor() \n" +
                "{\n" +
                "    /* Click on a large 14\" Pepperoni Pizza */\n" +
                "    document.getElementsByClassName('card__list-item__title')[1].click();\n" +
                "}\n" +
                "pageInteractor();";
        view.loadUrl(page);
        System.out.println("Going to checkout.");
        view.loadUrl(DominosPageFlow.CHECKOUT_PAGE.getURL());
        System.out.println("Leaving doMenuPage()");
    }

    private void doCheckoutPage(final WebView view)
    {
        System.out.println("Entering doCheckoutPage()");
        String page = "javascript:\n" +
                "/* wait for document to be ready and loaded before doing anything */\n" +
                "while (document.readyState !== 'complete');\n" +
                "function pageInteractor() \n" +
                "{\n" +
                "    function clearPromotion()\n" +
                "    {\n" +
                "        /* Close the promotional popup if it exists */\n" +
                "        var closePromotionLink = document.getElementsByClassName('js-nothanks')[0];\n" +
                "        if (closePromotionLink && closePromotionLink.click)\n" +
                "        {\n" +
                "            closePromotionLink.click();\n" +
                "        }\n" +
                "        /* Chain functions together to give the page a bit to load */\n" +
                "        setTimeout(clickCoke, 4000);\n" +
                "    }\n" +
                "    function clickCoke()\n" +
                "    {\n" +
                "        /* Get a 2 liter Coke */\n" +
                "        document.getElementsByName('Beverage_Selection')[0].click();\n" +
                "        /* Chain functions together to give the page a bit to load */\n" +
                "        setTimeout(clickAddCoke, 1000);\n" +
                "    }\n" +
                "    function clickAddCoke()\n" +
                "    {\n" +
                "        document.getElementsByClassName('js-isNew')[0].click();\n" +
                "        /* Chain functions together to give the page a bit to load */\n" +
                "        setTimeout(continueToPayment, 1000);\n" +
                "    }\n" +
                "    function continueToPayment()\n" +
                "    {\n" +
                "        /* Submit form to continue to payment page */\n" +
                "        document.getElementsByClassName('submitButton')[0].click();\n" +
                "    }\n" +
                "    clearPromotion();\n" +
                "}\n" +
                "pageInteractor();";
        view.loadUrl(page);
        System.out.println("Leaving doCheckoutPage()");
    }

    private void doPaymentPage(final WebView view)
    {
        System.out.println("Entering doPaymentPage()");
        String page = "javascript:\n" +
                "/* wait for document to be ready and loaded before doing anything */\n" +
                "while (document.readyState !== 'complete');\n" +
                "function pageInteractor()\n" +
                "{\n" +
                "    document.getElementById('First_Name').value = 'Bob';\n" +
                "    document.getElementById('Last_Name').value = 'Bobenstein';\n" +
                "    document.getElementById('Email').value = 'bob@gmail.com';\n" +
                "    document.getElementById('Callback_Phone').value = '5558675309';\n" +
                "    document.getElementById('Email_Opt_In').checked = false;\n" +
                "    document.evaluate( '/html/body//form//input[@type=\\\"radio\\\" and @name=\\\"Payment_Type\\\" and @value=\\\"Credit\\\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.checked = true;\n" +
                "    document.getElementById('Credit_Card_Number').value = '1234123412341234';\n" +
                "    var expMonthSelect = document.getElementById('Expiration_Month');\n" +
                "    for (var i=0; i<expMonthSelect.options.length; i++) \n" +
                "    {\n" +
                "        if (expMonthSelect.options[i].value && expMonthSelect.options[i].value == 1)\n" +
                "        {\n" +
                "            expMonthSelect.selectedIndex = i;\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "    var expYearSelect = document.getElementById('Expiration_Year');\n" +
                "    for (var i=0; i<expYearSelect.options.length; i++)\n" +
                "    {\n" +
                "        if (expYearSelect.options[i].value && expYearSelect.options[i].value == 2019)\n" +
                "        {\n" +
                "            expYearSelect.selectedIndex = i;\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "    document.getElementById('Credit_Card_Security_Code').value = '456';\n" +
                "    document.getElementById('Billing_Postal_Code').value = '50014';\n" +
                "    document.getElementsByClassName('submitButton')[0].click();\n" +
                "}\n" +
                "setTimeout(pageInteractor, 5000);";
        view.loadUrl(page);
        System.out.println("Leaving doPaymentPage()");
    }
}