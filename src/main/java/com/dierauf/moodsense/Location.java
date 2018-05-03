package com.dierauf.moodsense;


public class Location {


  private final String latitude;
  private final String longitude;
  private final String location;


  public Location(String location) {
    this.location = location;
    final String coords = location.substring(1, location.length() -1); // Strip off parenthesis
    final String[] latLong = coords.split(", ?");
    this.latitude = latLong[0];
    this.longitude = latLong[1];
  }


  public String getLatitude() {
    return this.latitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  @Override
  public String toString() {
    return this.location;
  }


  public static boolean isValid(String location) {
    if (location == null)
      return false;
    return location.matches("\\((\\-?\\d{1,3}\\.\\d+)([, ]+)([\\+\\-]*\\d{1,3}\\.\\d+)\\)");
  }


  public String getLocation() {
    return this.location;
  }
}
