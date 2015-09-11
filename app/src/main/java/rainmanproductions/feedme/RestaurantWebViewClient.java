package rainmanproductions.feedme; /**
 * Created by Matt C on 9/10/2015.
 */

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import rainmanproductions.feedme.AssetReader;


public class RestaurantWebViewClient extends WebViewClient
{
    RestaurantPageFlow pageFlow;

    RestaurantWebViewClient(RestaurantPageFlow pageFlow)
    {
        this.pageFlow = pageFlow;
    }

    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        System.out.println("Entering " + pageFlow.getRestaurantType() + " on page finished. url=" + url);
        RestaurantPageFlow page = pageFlow.getPageFromURL(url);
        try
        {
            doPage(view, page);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Leaving " + pageFlow.getRestaurantType() + " on page finished.");
    }

    private void doPage(final WebView view, final RestaurantPageFlow page) throws IOException
    {
        System.out.println("Entering doPage(page=" + page + ")");
        String js = AssetReader.readJsFile(page.getJsFilepath());
        view.loadUrl(js);
        System.out.println("Leaving doPage(page=" + page + ")");
    }
}