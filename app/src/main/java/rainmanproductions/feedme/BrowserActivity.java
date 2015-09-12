package rainmanproductions.feedme;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.restaurants.RestaurantWebViewClient;

public class BrowserActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("Entering BrowserActivity onCreate()");

        WebView webview = new WebView(this);
        AssetReader.setAssetManager(this);
        setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        CookieSyncManager.createInstance(this);//Deprecated but works well to clear cache
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        Restaurant restaurant = (Restaurant) getIntent().getExtras().get("restaurant");
        webview.setWebViewClient(new RestaurantWebViewClient(restaurant.getRestaurantPageFlow()));
        webview.loadUrl(restaurant.getRestaurantPageFlow().getStartingURL());
        System.out.println("Leaving BrowserActivity onCreate()");
    }
}
