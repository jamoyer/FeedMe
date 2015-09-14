package rainmanproductions.feedme.gps;

public class GPSLatLong
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

    public GPSLatLong(double latitude, double longitude)
    {
        this.latitude = latitude;

        this.longitude = longitude;
    }
}
