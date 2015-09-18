package rainmanproductions.feedme.gps;

import android.util.Log;

import java.util.List;

/**
 * To be the main API that the UI should use to handle getting the users current address or storing addresses.
 * <p/>
 * This API will handle the procedure of interacting with the GPSHandler or storing saved addresses.
 */
public class DeliveryAddressHandler
{
    private static final String LOG_PREFIX = "DeliveryAddressHandler";
    private static final int DISTANCE_THRESHOLD_METERS = 100;
    private static final int INITIAL_CLOSEST_INDEX = -1; //  need to initialize the closest index to something that can't be an index value (anything negative will work)

    /**
     * Gets an address based off of the user's current location and their previously stored addresses.
     *
     * @return An AddressInfo for their current location.
     * Never returns null, will return a blank AddressInfo if nothing is found.
     */
    public static AddressInfo getAddress()
    {
        Log.i(LOG_PREFIX, "Getting suggested address.");
        GPSHandler gpsHandler = GPSHandler.getInstance();
        if (gpsHandler == null)
        {
            Log.i(LOG_PREFIX, "GPSHandler is null. Returning blank AddressInfo");
            return new AddressInfo();
        }
        // check if we can get the location
        GPSLatLon location = gpsHandler.getLocation();
        if (location == null)
        {
            Log.i(LOG_PREFIX, "Last location is unknown. Returning blank AddressInfo");
            return new AddressInfo();
        }

        int closestCoordIndex = INITIAL_CLOSEST_INDEX;
        double closestCoordDist = Double.MAX_VALUE;
        List<AddressInfo> savedAddresses = StoredAddressesAccessor.getSavedAddressInfos();
        // find the closest saved address to this location
        for (int i = 0; i < savedAddresses.size(); i++)
        {
            // get a saved location and see if it is closer than our current closest address
            GPSLatLon savedLocation = savedAddresses.get(i).getLatLon();
            if (savedLocation == null)
            {
                continue;
            }
            double distance = GPSHandler.getDist(location, savedLocation);
            if (distance < closestCoordDist)
            {
                // this distance is closer, set the index of this element and the distance
                closestCoordDist = distance;
                closestCoordIndex = i;
            }
        }

        // check if no addresses were found
        if (closestCoordIndex == INITIAL_CLOSEST_INDEX)
        {
            Log.i(LOG_PREFIX, "No saved addresses.");
        }
        else
        {
            Log.i(LOG_PREFIX, "Searched through saved addresses, closest location is number: " +
                    closestCoordIndex + " at coord: " + savedAddresses.get(closestCoordIndex) +
                    " at distance away: " + closestCoordDist);
        }

        // check if distance is less than threshold or if we need to look address up.
        if (closestCoordDist <= DISTANCE_THRESHOLD_METERS)
        {
            Log.i(LOG_PREFIX, "Closest coord is closer than threshold, using it for suggestion.");
            return savedAddresses.get(closestCoordIndex);
        }
        Log.i(LOG_PREFIX, "Closest coord is further than threshold, looking up our location instead of using it.");
        return gpsHandler.lookupAddress(location.getLatitude(), location.getLongitude());
    }

    /**
     * Saves the address info to the store. Also tries to look up the GPS
     * Coordinate Latitude and Longitude of this address if they are not
     * already part of the AddressInfo.
     *
     * @param addressInfo The address info to be saved.
     */
    public static void saveAddress(final AddressInfo addressInfo)
    {
        Log.i(LOG_PREFIX, "Entering saveAddress(" + addressInfo + ")");

        if (addressInfo == null || !addressInfo.hasData())
        {
            String msg = "Cannot save empty address info.";
            Log.e(LOG_PREFIX, msg);
            throw new IllegalArgumentException(msg);
        }

        GPSHandler gpsHandler = GPSHandler.getInstance();
        List<AddressInfo> addressInfos = StoredAddressesAccessor.getSavedAddressInfos();

        // check if the address has already been saved
        for (int i = 0; i < addressInfos.size(); i++)
        {
            AddressInfo saved = addressInfos.get(i);
            // check if there is a saved equivalent address
            if (addressInfo.isSameAddress(saved))
            {
                // same address found, check if it needs a lat lon to be looked up
                Log.i(LOG_PREFIX, "Address is already saved.");
                if (saved.getLatLon() == null)
                {
                    // the saved lat lon does not have a latlon, try to get it by looking it up or using what addressInfo has
                    if (addressInfo.getLatLon() == null && gpsHandler != null)
                    {
                        // lookup latlon and save it
                        GPSLatLon latLon = gpsHandler.lookupLatLon(saved);
                        addressInfo.setLatLon(latLon);
                    }
                    if (addressInfo.getLatLon() != null)
                    {
                        // save the address if it contains a lat lon to use
                        StoredAddressesAccessor.setAddressInfoAtIndex(i, addressInfo);
                    }
                }
                // similar address found, no need to continue
                return;
            }
        }

        // check if there is anything we can pull from nearby addresses
        if (addressInfo.getLatLon() == null)
        {
            Log.i(LOG_PREFIX, "LatLon is null, checking if there are any similar addresses we can get the lat lon from.");
            for (AddressInfo saved : addressInfos)
            {
                // check if it only differs by a unit address and we can get the lat lon
                if (addressInfo.isSameStreetAddress(saved) && saved.getLatLon() != null)
                {
                    Log.i(LOG_PREFIX, "Same street address found with latlon. Taking its latlon.");
                    addressInfo.setLatLon(saved.getLatLon());
                    break;
                }
            }
        }

        // check if latlon is needed
        if (addressInfo.getLatLon() == null && gpsHandler != null)
        {
            Log.i(LOG_PREFIX, "LatLon needs to be looked up. Looking it up.");
            addressInfo.setLatLon(gpsHandler.lookupLatLon(addressInfo));
        }

        // save the address info
        Log.i(LOG_PREFIX, "Saving address");
        StoredAddressesAccessor.addAddressInfo(addressInfo);

        Log.i(LOG_PREFIX, "Leaving saveAddress()");
    }
}
