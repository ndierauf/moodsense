package com.dierauf.moodsense;

public class Capture {


  private final String username;
  private final CaptureType captureType;
  private final Location location;


  public Capture(String username, String captureType, String location) {
    this.username = username;
    this.captureType = CaptureType.valueOf(captureType);
    this.location = new Location(location);
  }


  public String getUsername() {
    return this.username;
  }

  public CaptureType getCaptureType() {
    return this.captureType;
  }

  public Location getLocation() {
    return this.location;
  }


  @Override
  public String toString() {
    return "username=" + this.username + "; captureType=" + this.captureType
            .getCaputureTypeValue() + "; location=" + this.location.toString() + ". ";
  }


  public static final void main(String[] args) {
    final CaptureType captureType =  CaptureType.valueOf(CaptureType.HAPPY.getCaputureTypeValue());
    System.out.println(captureType.getCaputureTypeValue());
    final String value = "HAPPY";
    if (CaptureType.contains(value))
      System.out.println("contains");
    else
      System.out.println("Does not contain");
    final String location = "\\(37.600823476961146, -122.38243281841278\\)";
    if (!Location.isValid(location))
      System.out.println("matches");
    else
      System.out.println("Doesn't match");
    final String location2 = "\\(37.600823476961146,-122.38243281841278\\)";
    if (!Location.isValid(location2))
      System.out.println("matches");
    else
      System.out.println("Doesn't match");
  }

}
