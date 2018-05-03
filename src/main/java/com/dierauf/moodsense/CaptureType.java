package com.dierauf.moodsense;


public enum CaptureType {
  HAPPY("HAPPY"),
  SAD("SAD"),
  NEUTRAL("NEUTRAL");

  private final String caputureType;

  private CaptureType(String captureType) {
    this.caputureType = captureType;
  }

  public String getCaputureTypeValue() {
    return this.caputureType;
  }

  public static boolean contains(final String value) {

    for (final CaptureType type : CaptureType.values()) {
      if (type.getCaputureTypeValue().equals(value)) {
        return true;
      }
    }
    return false;
  }

}
