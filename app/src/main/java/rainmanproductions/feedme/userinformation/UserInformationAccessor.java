package rainmanproductions.feedme.userinformation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserInformationAccessor
{

    private final SharedPreferences accessPoint;
    private static UserInformationAccessor instance;
    private static final String LOG_TAG = "UserInformationAccessor";

    private UserInformationAccessor(final Activity activity)
    {
        Log.i(LOG_TAG, "Initializing Accessor.");
        accessPoint = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public static void init(final Activity activity)
    {
        if (instance == null)
        {
            instance = new UserInformationAccessor(activity);
        }
    }

    public static UserInformationAccessor getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException("Singleton is not initialized!");
        }
        return instance;
    }

    public String getInfo(final AccessorKeyType type)
    {
        return getInfo(type.toString());
    }

    public void putInfo(final AccessorKeyType type, final String info)
    {
       putInfo(type.toString(), info);
    }

    public String getInfo(final String type)
    {
        String stored = accessPoint.getString(type, null);
        Log.i(LOG_TAG, "Got data: " + stored + " for " + type);
        return stored;
    }

    public void putInfo(final String type, final String info)
    {
        accessPoint.edit()
                .remove(type)
                .putString(type, info)
                .apply();
        Log.i(LOG_TAG, "Put data: " + info + " for " + type);
    }
}
