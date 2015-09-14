package rainmanproductions.feedme.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import rainmanproductions.feedme.userinformation.StoredAddressesAccessor;

public class GPSAccessor
{
    private static final String LOG_PREFIX = "GPSAccessor";

    private static GPSAccessor instance = null;

    private final LocationListener listener;
    private final LocationManager manager;
    private final Geocoder geocoder;
    private final int DISTANCE_THRESHOLD = 15;

    private GPSLatLong lastLocation;

    public static void initGPSAcessor(final Context context)
    {
        if (instance == null)
        {
            instance = new GPSAccessor(context);
        }
    }

    public static GPSAccessor getInstance()
    {
        return instance;
    }

    private GPSAccessor(final Context context)
    {
        geocoder = new Geocoder(context);

        // Acquire a reference to the system Location Manager
        this.manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try
        {
            Location lastKnownLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.lastLocation = new GPSLatLong(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
        catch (SecurityException e)
        {
            Log.e(LOG_PREFIX, "Insufficient Permissions to add gps listener: " + e.getMessage());
        }

        // Define a listener that responds to location updates
        this.listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                lastLocation = new GPSLatLong(location.getLatitude(), location.getLongitude());
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

    public void startGettingLocation()
    {
        // Register the listener with the Location Manager to receive location updates
        try
        {
            this.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this.listener);
        }
        catch (SecurityException e)
        {
            Log.e(LOG_PREFIX, "Insufficient Permissions to add gps listener: " + e.getMessage());
        }
    }

    public void stopGettingLocation()
    {
        // Register the listener with the Location Manager to receive location updates
        try
        {
            this.manager.removeUpdates(this.listener);
        }
        catch (SecurityException e)
        {
            Log.e(LOG_PREFIX, "Insufficient Permissions to remove gps listener: " + e.getMessage());
        }
    }

    public GPSLatLong getLastLocation()
    {
        return lastLocation;
    }

    public AddressInfo getAddress(final double latitude, final double longitude) throws IOException
    {
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        Address address = null;
        if (!addresses.isEmpty() && addresses.get(0) != null)
        {
            address = addresses.get(0);
        }
        else
        {
            return new AddressInfo();
        }
        AddressInfo addressInfo = new AddressInfo();

        addressInfo.setCity(address.getLocality());
        addressInfo.setCountry(address.getCountryName());
        addressInfo.setState(address.getAdminArea());
        addressInfo.setStreetAddress(address.getAddressLine(0));
        addressInfo.setZipCode(address.getPostalCode());

        return addressInfo;
    }

    @NonNull
    public AddressInfo getSuggestedAddress() throws IOException
    {
        int closestCoordinateIndex = -1;
        double closestCoordinateValue = Double.MAX_VALUE;
        List<GPSLatLong> savedAddresses = StoredAddressesAccessor.getSavedAddresses();
        for (int i = 0; i < savedAddresses.size(); i++)
        {
            GPSLatLong savedLocation = savedAddresses.get(i);
            double distance = getDist(lastLocation, savedLocation);
            if (distance < closestCoordinateValue)
            {
                closestCoordinateValue = distance;
                closestCoordinateIndex = i;
            }
        }

        if (closestCoordinateValue < DISTANCE_THRESHOLD)
        {
            try
            {
                return StoredAddressesAccessor.getAddressInfoByIndex(closestCoordinateIndex);
            }
            catch (NumberFormatException e)
            {
                Log.e(LOG_PREFIX, "Could not get closest coordinate value " + e.getMessage());
            }
        }
        return getAddress(lastLocation.getLatitude(), lastLocation.getLongitude());
    }

    private static double getDist(GPSLatLong loc1, GPSLatLong loc2)
    {
        float results[] = {0};
        Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude(), results);
        return results[0];
    }
}