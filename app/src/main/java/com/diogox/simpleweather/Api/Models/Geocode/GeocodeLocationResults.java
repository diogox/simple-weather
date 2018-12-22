package com.diogox.simpleweather.Api.Models.Geocode;

import java.util.List;

public class GeocodeLocationResults {
    private List<GeocodeCity> results;

    public String getLat() {
        if (results.size() > 0) {
            return results.get(0).geometry.location.lat;
        }
        return null;
    }

    public String getLon() {
        if (results.size() > 0) {
            return results.get(0).geometry.location.lng;
        }
        return null;
    }

    public String getPlaceId() {
        if (results.size() > 0) {
            return results.get(0).place_id;
        }
        return null;
    }

    public String getName() {
        if (results.size() > 0) {
            return results.get(0).address_components.get(0).short_name;
        }
        return null;
    }

    private class GeocodeCity {
        private String place_id;
        private List<GeocodeAddress> address_components;
        private GeocodeCityGeometry geometry;

        private class GeocodeCityGeometry {
            private Location location;

            private class Location {
                private String lat;
                private String lng;
            }
        }

        private class GeocodeAddress {
            private String short_name;
        }
    }
}
