package rainmanproductions.feedme.userinformation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rainmanproductions.feedme.gps.AddressInfo;
import rainmanproductions.feedme.gps.GPSLatLong;

public class StoredAddressesAccessor
{
    private static final String LOG_PREFIX = StoredAddressesAccessor.class.getSimpleName();

    public static List<GPSLatLong> getSavedAddresses()
    {
        List<GPSLatLong> retLatLong = new ArrayList<>();
        int numberOfEntries;
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        try
        {
            numberOfEntries = Integer.parseInt(accessor.getInfo(GPSType.GPS_NUMBER_OF_ENTRIES));
        }
        catch (NumberFormatException e)
        {
            Log.e(LOG_PREFIX, "Number of address entries is not an int " + e);
            return Collections.emptyList();
        }

        for (int i = 0; i < numberOfEntries; i++)
        {
            try
            {
                retLatLong.add(getLatLongByIndex(i));
            }
            catch (NumberFormatException e)
            {
                Log.e(LOG_PREFIX, "Could not convert lat and lon to integer. Bad store value " + e);
                removeKeyFromStore(i);
            }
        }

        return retLatLong;
    }

    private static void removeKeyFromStore(int i)
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (GPSType gpsInfo : GPSType.values())
        {
            if (!gpsInfo.equals(GPSType.GPS_NUMBER_OF_ENTRIES))
            {
                String key = gpsInfo.toString() + i;
                accessor.putInfo(key, null);
            }
        }
    }

    public static GPSLatLong getLatLongByIndex(int index) throws NumberFormatException
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        String gpsLatitudeKey = GPSType.GPS_LATITUDE.toString() + index;
        String gpsLatitudeValue = accessor.getInfo(gpsLatitudeKey);

        String gpsLongitudeKey = GPSType.GPS_LONGITUDE.toString() + index;
        String gpsLongitudeValue = accessor.getInfo(gpsLongitudeKey);

        double lat = Double.parseDouble(gpsLatitudeValue);
        double lon = Double.parseDouble(gpsLongitudeValue);

        return new GPSLatLong(lat, lon);
    }

    public static AddressInfo getAddressInfoByIndex(int index)
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        String streetAddress = accessor.getInfo(GPSType.GPS_STREET_ADDRESS.toString() + index);
        String unitNumber = accessor.getInfo(GPSType.GPS_UNIT_NUMBER.toString() + index);
        String zipCode = accessor.getInfo(GPSType.GPS_ZIP_CODE.toString() + index);
        String city = accessor.getInfo(GPSType.GPS_CITY.toString() + index);
        String stateName = accessor.getInfo(GPSType.GPS_STATE_NAME.toString() + index);
        String stateCode = accessor.getInfo(GPSType.GPS_STATE_CODE.toString() + index);
        String country = accessor.getInfo(GPSType.GPS_COUNTRY.toString() + index);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setCity(city);
        addressInfo.setCountry(country);
        addressInfo.setState(stateName);
        addressInfo.setStreetAddress(streetAddress);
        addressInfo.setUnitNumber(unitNumber);
        addressInfo.setZipCode(zipCode);
        return addressInfo;
    }
}
