package com.dierauf.moodsense;


public enum HappyLocation {

  WORK("(37.599186, -122.382682)"),
  BURGERS("(37.600566, -122.382424)"),
  GYM("(37.6002897, -122.3832501)");

  private Location location;

  HappyLocation(String location) {
    this.location = new Location(location);
  }

  public Location getLocation() {
    return this.location;
  }
}

