package rainmanproductions.feedme.restaurants;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /** Store value in keystore */
    @JavascriptInterface
    public void putInfo(final String key, final String val) {
        TemporaryKeyStore.putVal(key,val);
    }

    /** Retrieve value from keystore */
    @JavascriptInterface
    public String getInfo(final String key)
    {
        return TemporaryKeyStore.getVal(key);
    }

}
