package com.google.codeu.data;

public class Migrant {

    private int id;
    private String cause_of_death;
    private String region_origin;
    private int numDead;
    private String incident_region;
    private String date;
    private double latitude;
    private double longitude;
    
    
    public Migrant(int id, String cause_of_death, String region_origin,int dead, String incident_region, String date, double latitude, double longitude) {
		//super();
		this.id = id;
		this.cause_of_death = cause_of_death;
		this.region_origin = region_origin;
		this.numDead = dead;
		this.incident_region = incident_region;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCause_of_death() {
		return cause_of_death;
	}
	public void setCause_of_death(String cause_of_death) {
		this.cause_of_death = cause_of_death;
	}
	public String getRegion_origin() {
		return region_origin;
	}
	public void setRegion_origin(String region_origin) {
		this.region_origin = region_origin;
	}
	public int getDead() {
		return numDead;
	}
	public void setDead(int dead) {
		this.numDead = dead;
	}
	public String getIncident_region() {
		return incident_region;
	}
	public void setIncident_region(String incident_region) {
		this.incident_region = incident_region;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}