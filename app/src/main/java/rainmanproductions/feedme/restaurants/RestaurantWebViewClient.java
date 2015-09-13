package rainmanproductions.feedme.restaurants;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import rainmanproductions.feedme.AssetReader;
import rainmanproductions.feedme.userinformation.UserInfoPreprocessor;


public class RestaurantWebViewClient extends WebViewClient
{
    private static final String LOG_PREFIX = "RestaurantWebViewClient";
    RestaurantPageFlow pageFlow;

    public RestaurantWebViewClient(RestaurantPageFlow pageFlow)
    {
        this.pageFlow = pageFlow;
    }

    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        Log.i(LOG_PREFIX, "Entering " + pageFlow.getRestaurantType() + " on page finished. url=" + url);
        RestaurantPageFlow page = pageFlow.getPageFromURL(url);
        try
        {
            doPage(view, page);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.i(LOG_PREFIX, "Leaving " + pageFlow.getRestaurantType() + " on page finished.");
    }

    private void doPage(final WebView view, final RestaurantPageFlow page) throws IOException
    {
        Log.i(LOG_PREFIX, "Entering doPage(page=" + page + ")");
        String js = AssetReader.readJsFile(page.getJsFilepath());
        String prepprocessedJs = UserInfoPreprocessor.process(js);
        view.loadUrl(prepprocessedJs);
        Log.i(LOG_PREFIX, prepprocessedJs);
        Log.i(LOG_PREFIX, "Leaving doPage(page=" + page + ")");
    }
}