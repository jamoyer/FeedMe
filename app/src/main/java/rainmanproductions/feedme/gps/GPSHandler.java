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

public class GPSHandler
{
    private static final String LOG_PREFIX = "GPSHandler";
    private static final int DISTANCE_THRESHOLD_METERS = 100;

    private final LocationListener listener;
    private final LocationManager manager;
    private final Geocoder geocoder;

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
    private GPSHandler(final Context context)
    {
        Log.i(LOG_PREFIX, "Initializing GPSHandler.");
        geocoder = new Geocoder(context);

        // Acquire a reference to the system Location Manager
        this.manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try
        {
            Location lastKnownLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            this.lastLocation = new GPSLatLon(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
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
                lastLocation = new GPSLatLon(location.getLatitude(), location.getLongitude());
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

    /**
     * Begins updating location information. CAUTION : HIGH BATTERY USAGE, USE SPARINGLY!
     */
    public void startGettingLocation()
    {
        Log.i(LOG_PREFIX, "Starting GPS location tracking.");
        // Register the listener with the Location Manager to receive location updates
        try
        {
            this.manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this.listener);
        }
        catch (SecurityException e)
        {
            Log.e(LOG_PREFIX, "Insufficient Permissions to add gps listener: " + e.getMessage());
        }
    }

    /**
     * Ends updating location information.
     */
    public void stopGettingLocation()
    {
        Log.i(LOG_PREFIX, "Stopping GPS location tracking.");
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


    /**
     * Gets an address based off of the user's current location and their previously stored addresses.
     *
     * @return An AddressInfo for their current location.
     */
    public AddressInfo getAddress()
    {
        Log.i(LOG_PREFIX, "Getting suggested address.");
        int closestCoordIndex = -1;
        double closestCoordDist = Double.MAX_VALUE;
        List<AddressInfo> savedAddresses = StoredAddressesAccessor.getSavedAddressInfos();
        for (int i = 0; i < savedAddresses.size(); i++)
        {
            GPSLatLon savedLocation = savedAddresses.get(i).getLatLon();
            double distance = getDist(lastLocation, savedLocation);
            if (distance < closestCoordDist)
            {
                closestCoordDist = distance;
                closestCoordIndex = i;
            }
        }

        if (closestCoordIndex > -1)
        {
            Log.i(LOG_PREFIX, "Searched through saved addresses, closest location is number: " +
                    closestCoordIndex + " at coord: " + savedAddresses.get(closestCoordIndex) +
                    " at distance away: " + closestCoordDist);
        }
        else
        {
            Log.i(LOG_PREFIX, "No saved addresses.");
        }


        if (closestCoordDist <= DISTANCE_THRESHOLD_METERS)
        {
            Log.i(LOG_PREFIX, "Closest coord is closer than threshold, using it for suggestion.");
            return savedAddresses.get(closestCoordIndex);
        }

        try
        {
            Log.i(LOG_PREFIX, "Closest coord is further than threshold, looking up our location instead of using it.");
            return lookupAddress(lastLocation.getLatitude(), lastLocation.getLongitude());
        }
        catch (IOException e)
        {
            Log.e(LOG_PREFIX, "Could not lookup nearby address. Returning blank address info. " + e.getMessage());
            return new AddressInfo();
        }
    }

    /**
     * Saves the address info to the store.
     *
     * @param addressInfo The address info to be saved.
     */
    public void saveAddress(final AddressInfo addressInfo)
    {
        Log.i(LOG_PREFIX, "Entering saveAddress(" + addressInfo + ")");
        if (addressInfo == null || !addressInfo.hasData())
        {
            Log.e(LOG_PREFIX, "Address info is empty.");
            throw new IllegalArgumentException("Cannot save empty address info.");
        }
        List<AddressInfo> addressInfos = StoredAddressesAccessor.getSavedAddressInfos();

        // check if the address has already been saved
        for (AddressInfo saved : addressInfos)
        {
            // check if there is a saved equivalent address
            if (addressInfo.equals(saved) && saved.getLatLon() != null)
            {
                // nothing to do, the saved address works fine
                Log.i(LOG_PREFIX, "Address is already saved.");
                return;
            }
        }

        // check if there is anything we can pull from nearby addresses
        if (addressInfo.getLatLon() == null)
        {
            Log.i(LOG_PREFIX, "LatLon is null, checking if there are any similar addresses we can get the lat lon from.");
            for (int i = 0; i < addressInfos.size(); i++)
            {
                AddressInfo saved = addressInfos.get(i);

                // check if it only differs by a unit address
                if (addressInfo.isSameStreetAddress(saved))
                {
                    // check if we can get the lat lon from the saved equivalent street address
                    if (saved.getLatLon() != null)
                    {
                        if (saved.isSameAddress(saved))
                        {
                            Log.i(LOG_PREFIX, "Same entire address found. No need to save.");
                            return;
                        }
                        Log.i(LOG_PREFIX, "Same street address found at index: " + i + " with latlon. Taking its latlon.");
                        addressInfo.setLatLon(saved.getLatLon());
                        break;
                    }
                    Log.i(LOG_PREFIX, "Same street address found at index: " + i + " but it doesn't have a latlon.");
                }
            }
        }

        // check if latlon is needed
        if (addressInfo.getLatLon() == null)
        {
            Log.i(LOG_PREFIX, "LatLon needs to be looked up. Looking it up.");
            addressInfo.setLatLon(lookupLatLon(addressInfo));
        }

        // save the address info
        Log.i(LOG_PREFIX, "Saving address");
        StoredAddressesAccessor.addAddressInfo(addressInfo);
        Log.i(LOG_PREFIX, "Leaving saveAddress()");
    }

    /**
     * Looks up the AddressInfo at the given coordinate.
     *
     * @param latitude  Latitude of the coordinate.
     * @param longitude Longitude of the coordinate.
     * @return An AddressInfo of the given coordinate.
     * @throws IOException If the address cannot be looked up because of communcation reasons.
     */
    private AddressInfo lookupAddress(final double latitude, final double longitude) throws IOException
    {
        Log.i(LOG_PREFIX, "Looking up address for lat:" + latitude + " lon:" + longitude);
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        Address address;
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
    private GPSLatLon lookupLatLon(final AddressInfo addressInfo)
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
        Address address;
        // check nulls
        if (addresses == null || addresses.isEmpty() || (address = addresses.get(0)) == null)
        {
            Log.i(LOG_PREFIX, "Could not get latlon from Google.");
            return null;
        }

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
    private static double getDist(GPSLatLon loc1, GPSLatLon loc2)
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
}