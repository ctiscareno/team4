package com.google.codeu.data;

public class UserMarker {

  private double lat;
  private double lng;
  private String content;
  private String causeOfDeath;
  private String origin;
  private int numDead;
  private String incidentRegion;
  private String date;

  public UserMarker(double lat, double lng, String content, String causeOfDeath, String origin, int numDead, String incidentRegion, String date) {
    this.lat = lat;
    this.lng = lng;
    this.content = content;
    this.causeOfDeath = causeOfDeath;
    this.origin = origin;
    this.numDead = numDead;
    this.incidentRegion = incidentRegion;
    this.date = date;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getContent() {
    return content;
  }

  public String getCauseOfDeath() {
    return causeOfDeath;
  }

  public String getOrigin() {
    return origin;
  }

  public int getNumDead() {
    return numDead;
  }

  public String getIncidentRegion() {
    return incidentRegion;
  }

  public String getDate() {
    return date;
  }
}

