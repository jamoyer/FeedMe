package rainmanproductions.feedme.gps;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class StoredAddressesAccessor
{
    private static final String LOG_PREFIX = StoredAddressesAccessor.class.getSimpleName();
    private static final int MAX_STORES = 20;

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

    private static int getNumEntries() throws NumberFormatException
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

        if (gpsLatitudeValue == null || gpsLongitudeValue == null)
        {
            Log.e(LOG_PREFIX, "Lat or Lon values are null at index: " + index);
            throw new NumberFormatException("GPS Lat or Lon are null.");
        }
        double lat = Double.parseDouble(gpsLatitudeValue);
        double lon = Double.parseDouble(gpsLongitudeValue);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setCity(city);
        addressInfo.setCountry(country);
        addressInfo.setStateName(stateName);
        addressInfo.setStateCode(stateCode);
        addressInfo.setStreetAddress(streetAddress);
        addressInfo.setUnitNumber(unitNumber);
        addressInfo.setZipCode(zipCode);
        addressInfo.setLatLon(new GPSLatLon(lat, lon));

        Log.i(LOG_PREFIX, "Got address at index: " + index + " address: " + addressInfo);
        return addressInfo;
    }

    public static void addAddressInfo(final AddressInfo addressInfo)
    {
        int numEntries = getNumEntries();
        Log.i(LOG_PREFIX, "Adding addressinfo: " + addressInfo + " at index " + numEntries);
        setAddressInfoAtIndex(numEntries++, addressInfo);
        setNumEntries(numEntries);
    }

    private static void setNumEntries(int numEntries)
    {
        Log.i(LOG_PREFIX, "Setting number of address entries to " + numEntries);
        UserInformationAccessor.getInstance().putInfo(GPSType.GPS_NUMBER_OF_ENTRIES, numEntries + "");
    }

    private static void setAddressInfoAtIndex(int index, final AddressInfo addressInfo)
    {
        Log.i(LOG_PREFIX, "Setting index: " + index + " to address info: " + addressInfo);
        if (index < 0)
        {
            throw new IllegalArgumentException("Index must be non-negative.");
        }

        if (addressInfo == null)
        {
            throw new IllegalArgumentException("Address Info must be non-null.");
        }

        if (addressInfo.getLatLon() == null)
        {
            throw new IllegalArgumentException("Address Info LatLon must be non-null.");
        }

        Log.i(LOG_PREFIX, "Setting GPS entry at index " + index);
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        Double lat = addressInfo.getLatLon().getLatitude();
        Double lon = addressInfo.getLatLon().getLongitude();

        accessor.putInfo(GPSType.GPS_LATITUDE.toString() + index, lat.toString());
        accessor.putInfo(GPSType.GPS_LONGITUDE.toString() + index, lon.toString());
        accessor.putInfo(GPSType.GPS_STREET_ADDRESS.toString() + index, addressInfo.getStreetAddress());
        accessor.putInfo(GPSType.GPS_UNIT_NUMBER.toString() + index, addressInfo.getUnitNumber());
        accessor.putInfo(GPSType.GPS_ZIP_CODE.toString() + index, addressInfo.getZipCode());
        accessor.putInfo(GPSType.GPS_CITY.toString() + index, addressInfo.getCity());
        accessor.putInfo(GPSType.GPS_STATE_NAME.toString() + index, addressInfo.getStateName());
        accessor.putInfo(GPSType.GPS_STATE_CODE.toString() + index, addressInfo.getStateCode());
        accessor.putInfo(GPSType.GPS_COUNTRY.toString() + index, addressInfo.getCountry());
    }
}
