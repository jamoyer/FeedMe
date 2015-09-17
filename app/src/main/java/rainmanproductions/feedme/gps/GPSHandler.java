package rainmanproductions.feedme.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import rainmanproductions.feedme.userinformation.StateCodes;

/**
 * To be the main API for looking up addresses or getting the current location.
 */
public class GPSHandler
{
    private static final String LOG_PREFIX = "GPSHandler";

    private final LocationListener listener;
    private final LocationManager manager;
    private final Geocoder geocoder;
    private final Context context;

    private static GPSHandler instance = null;

    private GPSLatLon lastLocation;

    /**
     * Initializes the GPSHandler.
     *
     * @param context An application context or activity.
     */
    public static void init(final Context context)
    {
        if (instance == null)
        {
            instance = new GPSHandler(context);
        }
    }

    /**
     * @return The singleton instance of the GPSHandler.
     */
    public static GPSHandler getInstance()
    {
        return instance;
    }

    /**
     * Private constructor which sets up the geocoder and listens for changes in the location.
     *
     * @param context The context of the initializing activity.
     */
    private GPSHandler(final Context context) throws SecurityException
    {
        Log.i(LOG_PREFIX, "Initializing GPSHandler.");
        this.context = context;
        geocoder = new Geocoder(context);
        this.manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // try to initialize the location to our last known location using either gps or network
        if (isGPSLocationEnabled(context))
        {
            Location lastKnownLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null)
            {
                Log.i(LOG_PREFIX, "Initializing location from GPS.");
                this.lastLocation = new GPSLatLon(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }
        }
        if (lastLocation == null && isNetworkLocationEnabled(context))
        {
            Location lastKnownLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocation != null)
            {
                Log.i(LOG_PREFIX, "Initializing location from Network.");
                this.lastLocation = new GPSLatLon(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }
        }

        // Define a listener that responds to location updates
        this.listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                if (location != null)
                {
                    lastLocation = new GPSLatLon(location.getLatitude(), location.getLongitude());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
            }

            @Override
            public void onProviderEnabled(String provider)
            {
            }

            @Override
            public void onProviderDisabled(String provider)
            {
            }
        };
    }

    public GPSLatLon getLocation()
    {
        return lastLocation;
    }

    /**
     * Begins updating location information. CAUTION : HIGH BATTERY USAGE, USE SPARINGLY!
     */
    public void startGettingLocation() throws SecurityException
    {
        // start trying to get updates
        if (isGPSLocationEnabled(context))
        {
            Log.i(LOG_PREFIX, "Starting GPS location tracking.");
            this.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this.listener);
        }
        else if (isNetworkLocationEnabled(context))
        {
            Log.i(LOG_PREFIX, "Starting Network location tracking.");
            this.manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this.listener);
        }
    }

    /**
     * Ends updating location information.
     */
    public void stopGettingLocation() throws SecurityException
    {
        Log.i(LOG_PREFIX, "Stopping location tracking.");
        // Stop trying to get updates on the location
        this.manager.removeUpdates(this.listener);
    }


    /**
     * Looks up the AddressInfo at the given coordinate.
     *
     * @param latitude  Latitude of the coordinate.
     * @param longitude Longitude of the coordinate.
     * @return An AddressInfo of the given coordinate. AddressInfo will be blank on errors.
     */
    public AddressInfo lookupAddress(final double latitude, final double longitude)
    {
        Log.i(LOG_PREFIX, "Looking up address for lat:" + latitude + " lon:" + longitude);
        List<Address> addresses;
        try
        {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch (IOException e)
        {
            Log.i(LOG_PREFIX, "Could not get address from Google.");
            return new AddressInfo();
        }

        if (addresses == null || addresses.isEmpty() && addresses.get(0) == null)
        {
            Log.e(LOG_PREFIX, "Unable to lookup address from lat lon. Returning blank AddressInfo");
            return new AddressInfo();
        }
        Address address = addresses.get(0);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setCity(address.getLocality());
        addressInfo.setCountry(address.getCountryName());
        addressInfo.setStateName(address.getAdminArea());
        addressInfo.setStateCode(StateCodes.getCode(address.getAdminArea()));
        addressInfo.setLatLon(new GPSLatLon(latitude, longitude));
        addressInfo.setStreetAddress(address.getAddressLine(0));
        addressInfo.setZipCode(address.getPostalCode());

        Log.i(LOG_PREFIX, "Got AddressInfo: " + addressInfo);
        return addressInfo;
    }

    /**
     * Gets the GPSLatLon for a given AddressInfo
     *
     * @param addressInfo Address to look up the gps coords of
     * @return GPSLatLon of the given address or null if no results were returned.
     */
    public GPSLatLon lookupLatLon(final AddressInfo addressInfo)
    {
        Log.i(LOG_PREFIX, "Looking up address for " + addressInfo);
        // create query string from address info
        String queryString = "";
        queryString += addressInfo.getStreetAddress() + " ";
        queryString += addressInfo.getCity() + " ";
        queryString += addressInfo.getStateName() + " ";
        queryString += addressInfo.getZipCode() + " ";
        queryString += addressInfo.getCountry();

        // google for information about that address
        List<Address> addresses;
        try
        {
            addresses = geocoder.getFromLocationName(queryString, 1);
        }
        catch (IOException e)
        {
            Log.i(LOG_PREFIX, "Could not get latlon from Google.");
            return null;
        }

        // check nulls
        if (addresses == null || addresses.isEmpty() || addresses.get(0) == null)
        {
            Log.i(LOG_PREFIX, "Could not get latlon from Google.");
            return null;
        }
        Address address = addresses.get(0);

        // return the coordinate of the returned address
        GPSLatLon latLon = new GPSLatLon(address.getLatitude(), address.getLongitude());
        Log.i(LOG_PREFIX, "Got latlon: " + latLon);
        return latLon;
    }

    /**
     * Calculates the distance between two GPSLatLon objects in meters.
     *
     * @param loc1 GPSLatLon 1
     * @param loc2 GPSLatLon 2
     * @return The distance in meters between the two locations.
     */
    public static double getDist(GPSLatLon loc1, GPSLatLon loc2)
    {
        if (loc1 == null)
        {
            throw new IllegalArgumentException("Location 1 was null.");
        }
        if (loc2 == null)
        {
            throw new IllegalArgumentException("Location 2 was null.");
        }
        float results[] = {0};
        Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude(), results);
        double distance = results[0];
        Log.i(LOG_PREFIX, "Distance between location1: " + loc1 + " and location2: " + loc2 + " is " + distance);
        return distance;
    }

    public static boolean isGPSLocationEnabled(final Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetworkLocationEnabled(final Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isAnyLocationEnabled(final Context context)
    {
        return isGPSLocationEnabled(context) || isNetworkLocationEnabled(context);
    }
}