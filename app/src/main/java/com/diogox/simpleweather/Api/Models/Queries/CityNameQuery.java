package com.diogox.simpleweather.Api.Models.Queries;

public class CityNameQuery {
    private final String mCityName;
    private final String mCountryCode;

    public CityNameQuery(String cityName, String countryCode) {
        mCityName = cityName;
        mCountryCode = countryCode;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", mCityName, mCountryCode);
    }
}
