package com.diogox.simpleweather.Api.Models.Places;

import com.diogox.simpleweather.Api.PlacesClient;

import java.util.List;

public class CityDetails {
    transient private String html_attributions;
    transient private String status;
    private CityDetailsResult result;
    private String place_id;

    public String getLat() {
        return result.geometry.location.lat;
    }

    public String getLon() {
        return result.geometry.location.lng;
    }

    public String getPhotoUrl() {
        if (result.photos != null) {
            String photoReference = result.photos.get(0).photo_reference;
            return "https://maps.googleapis.com/maps/api/place/photo?photoreference=" + photoReference + "&key=" + PlacesClient.API_KEY + "&maxheight=600&maxwidth=600";
        }

        return null;
    }

    public String getPlaceId() {
        return place_id;
    }

    private class CityDetailsResult {
        private Geometry geometry;
        private List<Photos> photos;

        private class Geometry {
            private GeometryLocation location;

            private class GeometryLocation {
                private String lat;
                private String lng;
            }
        }

        private class Photos {
            private String photo_reference;
        }
    }
}
