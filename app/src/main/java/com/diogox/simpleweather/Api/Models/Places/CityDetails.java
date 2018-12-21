package com.diogox.simpleweather.Api.Models.Places;

import com.diogox.simpleweather.Api.PlacesClient;

import java.util.List;

public class CityDetails {
    transient private String html_attributions;
    transient private String status;
    private CityDetailsResult result;

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

    protected class CityDetailsResult {
        protected Geometry geometry;
        protected List<Photos> photos;

        protected class Geometry {
            protected GeometryLocation location;

            protected class GeometryLocation {
                protected String lat;
                protected String lng;
            }
        }

        protected class Photos {
            protected String photo_reference;
        }
    }
}
