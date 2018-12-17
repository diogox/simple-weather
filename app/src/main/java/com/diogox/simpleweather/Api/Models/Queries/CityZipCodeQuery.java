package com.diogox.simpleweather.Api.Models.Queries;

public class CityZipCodeQuery {
    private final String mZipCode;
    private final String mCountryCode;

    public CityZipCodeQuery(String zipCode, String countryCode) {
        mZipCode = zipCode;
        mCountryCode = countryCode;
    }

    public CityZipCodeQuery(String zipCode) {
        mZipCode = zipCode;
        mCountryCode = null;
    }

    @Override
    public String toString() {
        if (mCountryCode == null) {
            return mZipCode;
        }
        return String.format("%s,%s", mZipCode, mCountryCode);
    }
}
