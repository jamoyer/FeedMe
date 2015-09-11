package rainmanproductions.feedme.userinformation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserInformationAccessor
{

    private final SharedPreferences accessPoint;
    private static UserInformationAccessor instance;

    private UserInformationAccessor(final Activity activity)
    {
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

    public String getInfo(final InfoType type)
    {
        return accessPoint.getString(type.toString(), null);
    }

    public void putInfo(final InfoType type, final String info)
    {
        SharedPreferences.Editor editor = accessPoint.edit();
        editor.putString(type.toString(), info);
        editor.commit();
    }
}
