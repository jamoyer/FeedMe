package rainmanproductions.feedme.gps;

public class AddressInfo
{
    private String streetAddress;
    private String unitNumber;
    private String zipCode;
    private String city;
    private String stateName;
    private String stateCode;
    private String country;
    private GPSLatLon latLon;

    /**
     * This method will format a string so that it follows the AddressInfo consistent format.
     * This allows AddressInfos to easily be compared without having to worry about case, number of spaces...
     *
     * @param data
     * @return
     */
    private String formatData(final String data)
    {
        // lowercase, remove all trailing whitespace, and change all groups of spaces, tabs, or new lines to a single space.
        return data != null ? data.toLowerCase().trim().replaceAll("\\s+", " ") : null;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = formatData(streetAddress);
    }

    public String getUnitNumber()
    {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber)
    {
        this.unitNumber = formatData(unitNumber);
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = formatData(zipCode);
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = formatData(city);
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = formatData(stateName);
    }

    public String getStateCode()
    {
        return stateCode;
    }

    public void setStateCode(String stateCode)
    {
        this.stateCode = formatData(stateCode);
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = formatData(country);
    }

    public GPSLatLon getLatLon()
    {
        return latLon;
    }

    public void setLatLon(GPSLatLon latLon)
    {
        this.latLon = latLon;
    }

    /**
     * @return true if this AddressInfo has any data set, false otherwise.
     */
    public boolean hasData()
    {
        if (latLon != null)
        {
            return true;
        }
        if (streetAddress != null && !streetAddress.isEmpty())
        {
            return true;
        }
        if (unitNumber != null && !unitNumber.isEmpty())
        {
            return true;
        }
        if (zipCode != null && !zipCode.isEmpty())
        {
            return true;
        }
        if (city != null && !city.isEmpty())
        {
            return true;
        }
        if (stateName != null && !stateName.isEmpty())
        {
            return true;
        }
        if (stateCode != null && !stateCode.isEmpty())
        {
            return true;
        }
        if (country != null && !country.isEmpty())
        {
            return true;
        }
        return false;
    }

    public boolean hasEmptyField()
    {
        if (latLon == null)
        {
            return true;
        }
        if (streetAddress == null || streetAddress.isEmpty())
        {
            return true;
        }
        if (unitNumber == null || unitNumber.isEmpty())
        {
            return true;
        }
        if (zipCode == null || zipCode.isEmpty())
        {
            return true;
        }
        if (city == null || city.isEmpty())
        {
            return true;
        }
        if (stateName == null || stateName.isEmpty())
        {
            return true;
        }
        if (stateCode == null || stateCode.isEmpty())
        {
            return true;
        }
        if (country == null || country.isEmpty())
        {
            return true;
        }
        return false;
    }

    /**
     * Checks if all address fields (except unit number and state code) are the same.
     *
     * @param that
     * @return
     */
    public boolean isSameStreetAddress(final AddressInfo that)
    {
        if (that == null)
        {
            return false;
        }
        if (streetAddress != null ? !streetAddress.equals(that.streetAddress) : that.streetAddress != null)
        {
            return false;
        }
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null)
        {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null)
        {
            return false;
        }
        if (stateName != null ? !stateName.equals(that.stateName) : that.stateName != null)
        {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null)
        {
            return false;
        }
        return true;
    }

    public boolean isSameAddress(final AddressInfo that)
    {
        if (that == null)
        {
            return false;
        }
        if (streetAddress != null ? !streetAddress.equals(that.streetAddress) : that.streetAddress != null)
        {
            return false;
        }
        if (unitNumber != null ? !unitNumber.equals(that.unitNumber) : that.unitNumber != null)
        {
            return false;
        }
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null)
        {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null)
        {
            return false;
        }
        if (stateName != null ? !stateName.equals(that.stateName) : that.stateName != null)
        {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null)
        {
            return false;
        }
        return true;
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

        AddressInfo that = (AddressInfo) o;

        if (streetAddress != null ? !streetAddress.equals(that.streetAddress) : that.streetAddress != null)
        {
            return false;
        }
        if (unitNumber != null ? !unitNumber.equals(that.unitNumber) : that.unitNumber != null)
        {
            return false;
        }
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null)
        {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null)
        {
            return false;
        }
        if (stateName != null ? !stateName.equals(that.stateName) : that.stateName != null)
        {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null)
        {
            return false;
        }
        return !(latLon != null ? !latLon.equals(that.latLon) : that.latLon != null);

    }

    @Override
    public int hashCode()
    {
        int result = streetAddress != null ? streetAddress.hashCode() : 0;
        result = 31 * result + (unitNumber != null ? unitNumber.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (latLon != null ? latLon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "AddressInfo{" +
                "streetAddress='" + streetAddress + '\'' +
                ", unitNumber='" + unitNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", country='" + country + '\'' +
                ", latLon=" + latLon +
                '}';
    }
}