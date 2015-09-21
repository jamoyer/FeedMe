package rainmanproductions.feedme.controllers;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.restaurants.RestaurantWebViewClient;
import rainmanproductions.feedme.restaurants.WebAppInterface;

public class BrowserActivity extends Activity
{
    private static final String LOG_PREFIX = "BrowserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("Entering BrowserActivity onCreate()");

        WebView webview = new WebView(this);

        // enable javascript
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //webview.setVisibility(View.GONE);

        // clear all saved browser data
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        CookieSyncManager.createInstance(this);//Deprecated but works well to clear cache
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        // show browser
        setContentView(webview);

        // go to the first page of the chosen restaurant
        Restaurant restaurant = (Restaurant) getIntent().getExtras().get("restaurant");
        if (restaurant != null)
        {
            webview.setWebViewClient(new RestaurantWebViewClient(restaurant.getRestaurantPageFlow()));
            webview.loadUrl(restaurant.getRestaurantPageFlow().getStartingURL());
        }
        Log.i(LOG_PREFIX, "Leaving BrowserActivity onCreate()");
    }
}