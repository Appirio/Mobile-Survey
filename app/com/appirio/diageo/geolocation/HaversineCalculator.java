package com.appirio.mobilesurvey.geolocation;

public class HaversineCalculator {

	 public static final double R = 6372.8; // In kilometers

	 public static double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
	       double dLat = Math.toRadians(lat2 - lat1);
	       double dLon = Math.toRadians(lon2 - lon1);
	       lat1 = Math.toRadians(lat1);
	       lat2 = Math.toRadians(lat2);
	 
	       double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + 
	    		   Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
	       double c = 2 * Math.asin(Math.sqrt(a));
	       return (R * c) / 1.609344;
	  }	 
	 
	 
	 public static void main(String[] args) {
	       System.out.println(calculateHaversine(36.12, -86.67, 33.94, -118.40));
	 }	 
	 
}
