package com.diogox.simpleweather.Api.Models.Places;

public class AutocompleteResultItem {
    private String description;
    private String id;
    transient private String matched_substrings;
    private String place_id;
    private String reference;
    private StructuredFormatting structured_formatting;
    transient private String terms;
    transient private String types;

    public String getName() {
        return structured_formatting.main_text;
    }

    public String getId() {
        return id;
    }

    public String getPlaceId() {
        return place_id;
    }

    public String getReference() {
        return reference;
    }

    private class StructuredFormatting {
        private String main_text; // City Name
        private String secondary_text; // Country Name
    }
}
