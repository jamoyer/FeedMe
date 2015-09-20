package rainmanproductions.feedme.gps;

/**
 * A simple class which holds a latitude and a longitude. This class is immutable.
 */
public class GPSLatLon
{
    private final double latitude;
    private final double longitude;

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    @Override
    public String toString()
    {
        return "GPSLatLon{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        GPSLatLon latLon = (GPSLatLon) o;

        if (Double.compare(latLon.latitude, latitude) != 0)
        {
            return false;
        }
        return Double.compare(latLon.longitude, longitude) == 0;

    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Constructs a new GPSLatLon from the given latitude and longitude values. Once set, these
     * values may not change during the lifetime of the object.
     *
     * @param latitude  The number of degrees north or south of the earth's equator (90 -> -90).
     * @param longitude The number of degrees east or west of the earth's prime meridian (180 - -180)
     */
    public GPSLatLon(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
