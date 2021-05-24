package com.mitya.getouts;

public class MarkerInfo {
    private double lat;
    private double lng;
    private String name;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MarkerInfo(double lat, double lng, String name, String time) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
