package com.diogox.simpleweather.Api.Models.Weather.Area;

import com.diogox.simpleweather.Api.Models.Weather.City.Main;

import java.util.List;

public class AreaWeather {
    private List<AreaCityWeather> list;

    public List<AreaCityWeather> getList() {
        return list;
    }

    public class AreaCityWeather {
        private String id;
        private String name;
        private Coord coord;
        //private Main main;

        public double getLat() {
            return Double.parseDouble(coord.lat);
        }

        public double getLon() {
            return Double.parseDouble(coord.lon);
        }

        private class Coord {
            private String lat;
            private String lon;
        }
    }
}
