package com.dierauf.moodsense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class CaptureServlet extends HttpServlet {

  private static final long serialVersionUID = 7680228194793230882L;
  private final static Set<String> USER_ACCOUNTS = new HashSet<>(23);
  private final static List<Capture> CAPTURES = new ArrayList<>(23);
  static {
    USER_ACCOUNTS.add("nick");
    USER_ACCOUNTS.add("nalin");
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException {

    this.doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException {

    final ServletOutputStream outputStream = response.getOutputStream();
    final String username = request.getParameter("username");
    final String captureType = request.getParameter("captureType");
    final String location = request.getParameter("location");
    if (!this.verifyParameters(outputStream, username, captureType, location))
      return;

    final Capture capture = new Capture(username, captureType, location);
    CAPTURES.add(capture);

    this.displayMoodFrequencyDistribution(outputStream, capture);
    this.displayProximityToLocations(outputStream, capture);
  }


  private void displayProximityToLocations(ServletOutputStream outputStream,
          Capture capture) throws IOException {

    outputStream.println("Proximity to Locations for capture (" + capture.getCaptureType().getCaputureTypeValue() + "): ");
    final String distanceToWork = this.getDistance(outputStream, capture.getLocation(), HappyLocation.WORK.getLocation());
    final String distanceToBurgers = this.getDistance(outputStream, capture.getLocation(), HappyLocation.BURGERS.getLocation());
    final String distanceToGym = this.getDistance(outputStream, capture.getLocation(), HappyLocation.GYM.getLocation());
    outputStream.println("Work: " + distanceToWork + "<br>In-and-Out Burgers: " + distanceToBurgers + "<br>Gym: " + distanceToGym);
  }


  private void displayMoodFrequencyDistribution(ServletOutputStream outputStream,
          Capture capture) throws IOException {

    final String username = capture.getUsername();
    outputStream.println("Mood Distribution for user '" + username + "': ");
    final List<Capture> userCaptures = new ArrayList<>(23);
    for (final Capture userCapture : CAPTURES) {
      if (username.equals(userCapture.getUsername()))
        userCaptures.add(userCapture);
    }

    int happyCount = 0;
    int sadCount = 0;
    int neutralCount = 0;

    for (final Capture userCapture : userCaptures) {
      final CaptureType captureType = userCapture.getCaptureType();
      if (captureType.equals(CaptureType.HAPPY))
        happyCount++;
      else if (captureType.equals(CaptureType.SAD))
        sadCount++;
      else if (captureType.equals(CaptureType.NEUTRAL))
        neutralCount++;
      else
        throw new RuntimeException("Unknown CaptureType: '" + captureType + "'. ");
    }

    outputStream.println("Happy: " + happyCount + ", Sad: " + sadCount + ", Neutral: " + neutralCount + "<br>");
  }


  private String getDistance(ServletOutputStream outputStream, Location location, Location happyLocation) throws IOException {

    final String locationLatitude = location.getLatitude();
    final String locationLongitude = location.getLongitude();
    final String happyLocationLatitude = happyLocation.getLatitude();
    final String happyLocationLongitude = happyLocation.getLongitude();

    final String originsAndDestinations = "origins=" + locationLatitude + "," + locationLongitude + "&destinations=" + happyLocationLatitude + "," + happyLocationLongitude;

    // ie: "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&key=AIzaSyCue0j4sRZ-AoYXG6s14oWKd4uPTg1pnZE&origins=37.598328627191904,-122.3815766243286&destinations=37.599186,-122.382682;
    final String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&key=AIzaSyCue0j4sRZ-AoYXG6s14oWKd4uPTg1pnZE&" + originsAndDestinations;
    final URL url = new URL(urlString);

    final URLConnection openConnection = url.openConnection();
    final BufferedReader in = new BufferedReader(
            new InputStreamReader(openConnection.getInputStream()));
    String inputLine;
    final StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
    final String distance = jsonObject.get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray().get(0).getAsJsonObject().get("distance").getAsJsonObject().get("text").getAsString();

    return distance;
  }


  private boolean verifyParameters(ServletOutputStream outputStream, String username,
          String captureType, String location) throws IOException {

    if (!USER_ACCOUNTS.contains(username)) {
      outputStream.println("Access denied. (Try using one of these User Names: nick nalin)");
      return false;
    }

    if (!CaptureType.contains(captureType)) {
      outputStream.println("Unknown capture type: '" + captureType + "'. ");
      return false;
    }

    if (!Location.isValid(location)) {
      outputStream.println("Invalid coordinates: '" + location + "'. ");
      return false;
    }

    return true;
  }
}
