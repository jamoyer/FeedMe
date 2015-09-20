package rainmanproductions.feedme.gps;


import rainmanproductions.feedme.userinformation.AccessorKeyType;

/**
 * This enum holds keys which are appended to and used to store previous addresses of the user to be
 * used later presumably.
 */
public enum GPSType implements AccessorKeyType
{
    GPS_NUMBER_OF_ENTRIES,
    GPS_LATITUDE,
    GPS_LONGITUDE,
    GPS_STREET_ADDRESS,
    GPS_UNIT_NUMBER,
    GPS_ZIP_CODE,
    GPS_CITY,
    GPS_STATE_NAME,
    GPS_STATE_CODE,
    GPS_COUNTRY
}