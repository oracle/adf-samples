/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfinsider.localdb.mobile.bo;


public class CountryBO implements Comparable{
    
    private String countryId;
    private String countryName;
    private Integer regionId;


    public CountryBO() {
        super();
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CountryBO)) {
            return false;
        }
        final CountryBO other = (CountryBO)object;
        if (!(countryId == null ? other.countryId == null : countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((countryId == null) ? 0 : countryId.hashCode());
        return result;
    }

    public int compareTo(Object o) {
        if (this.equals(o)){
            return 0;
        }
        else if (o instanceof CountryBO){
            return this.countryName.compareTo(((CountryBO)o).getCountryName());
        }
        else{
            throw new ClassCastException("CountryBO expected.");
        }
    }
}
