package rainmanproductions.feedme.dominos;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import rainmanproductions.feedme.AssetReader;

public class DominosWebViewClient extends WebViewClient
{
    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        System.out.println("Entering Dominos on page finished. url=" + url);
        DominosPageFlow page = DominosPageFlow.getPageFromURL(url);
        try
        {
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Leaving Dominos on page finished.");
    }

    private void doAddressPage(final WebView view) throws IOException
    {
        System.out.println("Entering doAddressPage()");
        String page = AssetReader.readTextAsset(DominosPageFlow.ADDRESS_PAGE.getJsFilepath());
        view.loadUrl(page);
        System.out.println("Leaving doAddressPage()");
    }

    private void doMenuPage(final WebView view) throws IOException
    {
        System.out.println("Entering doMenuPage()");
        String page = AssetReader.readTextAsset(DominosPageFlow.MENU_PAGE.getJsFilepath());
        view.loadUrl(page);
        System.out.println("Going to checkout.");
        view.loadUrl(DominosPageFlow.CHECKOUT_PAGE.getURL());
        System.out.println("Leaving doMenuPage()");
    }

    private void doCheckoutPage(final WebView view) throws IOException
    {
        System.out.println("Entering doCheckoutPage()");
        String page = AssetReader.readTextAsset(DominosPageFlow.CHECKOUT_PAGE.getJsFilepath());
        view.loadUrl(page);
        System.out.println("Leaving doCheckoutPage()");
    }

    private void doPaymentPage(final WebView view) throws IOException
    {
        System.out.println("Entering doPaymentPage()");
        String page = AssetReader.readTextAsset(DominosPageFlow.PAYMENT_PAGE.getJsFilepath());
        view.loadUrl(page);
        System.out.println("Leaving doPaymentPage()");
    }
}