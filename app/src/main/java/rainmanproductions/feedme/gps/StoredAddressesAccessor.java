package rainmanproductions.feedme.gps;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rainmanproductions.feedme.userinformation.UserInformationAccessor;

/**
 * Responsible for storing saved addresses of the user for use later when we are trying to determine
 * their location and address information.
 */
public class StoredAddressesAccessor
{
    private static final String LOG_PREFIX = StoredAddressesAccessor.class.getSimpleName();
    public static final int MAX_STORES = 20;

    /**
     * Gets all the saved addressses as an immutable List<AddressInfo> object.
     *
     * @return An immutable List<AddressInfo> object.
     */
    public static List<AddressInfo> getSavedAddressInfos()
    {
        Log.i(LOG_PREFIX, "Getting list of saved addresses.");
        List<AddressInfo> retLatLong = new ArrayList<>();

        boolean hasBadEntries = false;
        int numberOfEntries = getNumEntries();
        for (int i = 0; i < numberOfEntries; i++)
        {
            try
            {
                AddressInfo addressInfo = getAddressInfoAtIndex(i);
                retLatLong.add(addressInfo);
            }
            catch (NumberFormatException e)
            {
                Log.e(LOG_PREFIX, "Could not convert lat and lon to doubles at index: " + i + ". " + e.getMessage());
                removeKeyFromStore(i);
                hasBadEntries = true;
            }
        }

        // if there were any bad entries which were removed, squash the store down.
        if (hasBadEntries)
        {
            squashStore();
        }

        return Collections.unmodifiableList(retLatLong);
    }

    /**
     * Gets the number of entries stored in the address store. If the number is corrupted, this
     * method will squash all the entries to use the lowest indices of the store space and return
     * the newly calculated number of entries.
     *
     * @return The number of entries in the address store.
     */
    private static int getNumEntries()
    {
        Log.i(LOG_PREFIX, "Getting number of gps entries.");
        int numberOfEntries;
        try
        {
            final String numEntriesString = UserInformationAccessor.getInstance().getInfo(GPSType.GPS_NUMBER_OF_ENTRIES);

            // check if GPS_NUMBER_OF_ENTRIES has ever been set
            if (numEntriesString == null)
            {
                numberOfEntries = 0;
                setNumEntries(numberOfEntries);
            }
            else
            {
                numberOfEntries = Integer.parseInt(numEntriesString);
            }
        }
        catch (NumberFormatException e)
        {
            Log.e(LOG_PREFIX, "Number of address entries is not an int " + e.getMessage());
            Log.i(LOG_PREFIX, "Need to squash store and recalculate size.");
            numberOfEntries = squashStore();
        }
        Log.i(LOG_PREFIX, "Number of entries is " + numberOfEntries);
        return numberOfEntries;
    }

    /**
     * Removes all holes in the store and returns the size of the store.
     * NOTE: Because getNumEntries calls this method we should not call that method from here.
     * It may cause an infinte loop.
     *
     * @return The size of the store.
     */
    private static int squashStore()
    {
        Log.i(LOG_PREFIX, "Squashing store down.");
        // get all stored address info objects and put into a list
        // also remove all values from store
        List<AddressInfo> addresses = new ArrayList<>(MAX_STORES);
        for (int i = 0; i < MAX_STORES; i++)
        {
            AddressInfo addressInfo = getAddressInfoAtIndex(i);
            if (addressInfo.hasData())
            {
                addresses.add(addressInfo);
            }
            removeKeyFromStore(i);
        }

        // put all addresses from list back into store, with no gaps
        for (int i = 0; i < addresses.size(); i++)
        {
            setAddressInfoAtIndex(i, addresses.get(i));
        }

        // save the size into GPS_NUMBER_OF_ENTRIES entry
        setNumEntries(addresses.size());
        Log.i(LOG_PREFIX, "Store squashed, " + GPSType.GPS_NUMBER_OF_ENTRIES.toString() + " is " + addresses.size());

        // return the size
        return addresses.size();
    }

    /**
     * Removes all fields of the AddressInfo stored in the key store at the index.
     *
     * @param index The 0-indexed index to distinguish which AddressInfo to remove.
     */
    private static void removeKeyFromStore(int index)
    {
        Log.i(LOG_PREFIX, "Removing GPS entry at index " + index);
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (GPSType gpsInfo : GPSType.values())
        {
            if (!gpsInfo.equals(GPSType.GPS_NUMBER_OF_ENTRIES))
            {
                String key = gpsInfo.toString() + index;
                accessor.putInfo(key, null);
            }
        }
    }

    /**
     * Retrieves the AddressInfo stored in the key store at this 0-indexed index.
     *
     * @param index A 0-indexed index specifying the location of the AddressInfo.
     * @return The AddressInfo stored at the index.
     * @throws NumberFormatException When the latitude and longitude cannot be parsed as doubles.
     */
    private static AddressInfo getAddressInfoAtIndex(int index) throws NumberFormatException
    {
        Log.i(LOG_PREFIX, "Getting GPS entry at index " + index);
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        String streetAddress = accessor.getInfo(GPSType.GPS_STREET_ADDRESS.toString() + index);
        String unitNumber = accessor.getInfo(GPSType.GPS_UNIT_NUMBER.toString() + index);
        String zipCode = accessor.getInfo(GPSType.GPS_ZIP_CODE.toString() + index);
        String city = accessor.getInfo(GPSType.GPS_CITY.toString() + index);
        String stateName = accessor.getInfo(GPSType.GPS_STATE_NAME.toString() + index);
        String stateCode = accessor.getInfo(GPSType.GPS_STATE_CODE.toString() + index);
        String country = accessor.getInfo(GPSType.GPS_COUNTRY.toString() + index);
        String gpsLatitudeValue = accessor.getInfo(GPSType.GPS_LATITUDE.toString() + index);
        String gpsLongitudeValue = accessor.getInfo(GPSType.GPS_LONGITUDE.toString() + index);

        GPSLatLon gpsLatLon = null;
        if (gpsLatitudeValue != null && gpsLongitudeValue != null)
        {
            double lat = Double.parseDouble(gpsLatitudeValue);
            double lon = Double.parseDouble(gpsLongitudeValue);
            gpsLatLon = new GPSLatLon(lat, lon);
        }

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setCity(city);
        addressInfo.setCountry(country);
        addressInfo.setStateName(stateName);
        addressInfo.setStateCode(stateCode);
        addressInfo.setStreetAddress(streetAddress);
        addressInfo.setUnitNumber(unitNumber);
        addressInfo.setZipCode(zipCode);
        addressInfo.setLatLon(gpsLatLon);

        Log.i(LOG_PREFIX, "Got address at index: " + index + " address: " + addressInfo);
        return addressInfo;
    }

    /**
     * Adds an AddressInfo to the store at the smallest unused index..
     *
     * @param addressInfo The AddressInfo to store.
     */
    public static void addAddressInfo(final AddressInfo addressInfo)
    {
        int numEntries = getNumEntries();
        Log.i(LOG_PREFIX, "Adding addressinfo: " + addressInfo + " at index " + numEntries);
        setAddressInfoAtIndex(numEntries++, addressInfo);
        setNumEntries(numEntries);
    }

    /**
     * Sets the GPSType.GPS_NUMBER_OF_ENTRIES value of the keystore to numEntries.
     *
     * @param numEntries The number of entries in the key store.
     */
    private static void setNumEntries(int numEntries)
    {
        Log.i(LOG_PREFIX, "Setting number of address entries to " + numEntries);
        UserInformationAccessor.getInstance().putInfo(GPSType.GPS_NUMBER_OF_ENTRIES, numEntries + "");
    }

    /**
     * Puts the AddressInfo into the keystore at the index location.
     *
     * @param index       A 0-indexed location to put the AddressInfo.
     * @param addressInfo The AddressInfo to store.
     */
    public static void setAddressInfoAtIndex(int index, final AddressInfo addressInfo)
    {
        Log.i(LOG_PREFIX, "Setting index: " + index + " to address info: " + addressInfo);
        if (index < 0 || MAX_STORES <= index)
        {
            throw new IllegalArgumentException("Index must be between 0 and " + MAX_STORES);
        }

        if (addressInfo == null)
        {
            throw new IllegalArgumentException("Address Info must be non-null.");
        }

        // if there was no entry stored here before we need to update the count
        AddressInfo saved = getAddressInfoAtIndex(index);
        if (!saved.hasData())
        {
            setNumEntries(getNumEntries() + 1);
        }

        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        Log.i(LOG_PREFIX, "Setting GPS entry at index " + index);

        // convert the doubles latitude and longitude into string representations
        GPSLatLon latLon = addressInfo.getLatLon();
        final String lat = (latLon != null) ? (latLon.getLatitude() + "") : null;
        final String lon = (latLon != null) ? (latLon.getLongitude() + "") : null;

        // store all the values
        accessor.putInfo(GPSType.GPS_LATITUDE.toString() + index, lat);
        accessor.putInfo(GPSType.GPS_LONGITUDE.toString() + index, lon);
        accessor.putInfo(GPSType.GPS_STREET_ADDRESS.toString() + index, addressInfo.getStreetAddress());
        accessor.putInfo(GPSType.GPS_UNIT_NUMBER.toString() + index, addressInfo.getUnitNumber());
        accessor.putInfo(GPSType.GPS_ZIP_CODE.toString() + index, addressInfo.getZipCode());
        accessor.putInfo(GPSType.GPS_CITY.toString() + index, addressInfo.getCity());
        accessor.putInfo(GPSType.GPS_STATE_NAME.toString() + index, addressInfo.getStateName());
        accessor.putInfo(GPSType.GPS_STATE_CODE.toString() + index, addressInfo.getStateCode());
        accessor.putInfo(GPSType.GPS_COUNTRY.toString() + index, addressInfo.getCountry());
    }
}