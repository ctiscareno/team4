package com.google.codeu.data;

public class TypeDeaths {
	private int drown;
	private int vehicle;
	private int exposure;
	private int violence;
	private int other;
	
	
	public TypeDeaths(int drown, int vehicle, int exposure, int violence, int other) {
		super();
		this.drown = drown;
		this.vehicle = vehicle;
		this.exposure = exposure;
		this.violence = violence;
		this.other = other;
	}
	
	public int getDrown() {
		return drown;
	}

	public void setDrown(int drown) {
		this.drown = drown;
	}

	public int getVehicle() {
		return vehicle;
	}

	public void setVehicle(int vehicle) {
		this.vehicle = vehicle;
	}

	public int getExposure() {
		return exposure;
	}

	public void setExposure(int exposure) {
		this.exposure = exposure;
	}

	public int getViolence() {
		return violence;
	}

	public void setViolence(int violence) {
		this.violence = violence;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}


}
