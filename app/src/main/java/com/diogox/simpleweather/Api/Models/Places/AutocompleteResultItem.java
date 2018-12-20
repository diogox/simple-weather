package com.diogox.simpleweather.Api.Models.Places;

public class AutocompleteResultItem {
    private String description;
    private String id;
    transient private String matched_substrings;
    private String place_id;
    private String reference;
    transient private String structured_formatting;
    transient private String terms;
    transient private String types;

    public String getDescription() {
        return description;
    }
}
