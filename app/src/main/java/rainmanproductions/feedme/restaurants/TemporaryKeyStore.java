package rainmanproductions.feedme.restaurants;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TemporaryKeyStore {

    private final static Map<String, String> savedValues = new HashMap<>(); //TODO:look into hashmap implementation to see how overwrites are handled
    private static final String LOG_TAG = "TemporaryKeyStore";

    public static String getVal(String key)
    {
        String stored = savedValues.get(key);
        Log.i(LOG_TAG, "Got data: " + stored);
        return savedValues.get(key);
    }

    public static void putVal(String key, String val)
    {
        savedValues.put(key, val);
        Log.i(LOG_TAG, "Put data: " + val + " for " + key);
    }


}
