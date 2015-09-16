package rainmanproductions.feedme.gps;

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

    public GPSLatLon(double latitude, double longitude)
    {
        this.latitude = latitude;

        this.longitude = longitude;

    }
}
