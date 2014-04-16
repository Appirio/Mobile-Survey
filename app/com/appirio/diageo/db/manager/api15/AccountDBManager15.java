package com.appirio.diageo.db.manager.api15;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api14.AccountDBManager14;
import com.appirio.diageo.geolocation.HaversineCalculator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class AccountDBManager15 extends AccountDBManager14 {

	public AccountDBManager15() throws DiageoServicesException {
		super();
	}
	
	public JsonNode findAccounts(Double latitude, Double longitude, Double radius, long limit) throws DiageoServicesException {
		double degrees = radius / 50;
		
		ArrayNode result = queryToJson("select latitude__c, longitude__c, tdlinx_outlet_city__c,  tdlinx_outlet_zip_code__c,  tdlinx_outlet_state__c,  tdlinx_outlet_addr__c,  billingstate,  billingcountry,  billingpostalcode, billingcity,  billingstreet,  sfid,  TDLinx_Outlet_Desc__c,  Name,  TDLinx_Outlet_State__c,  (|/( power(Latitude__c - " + latitude.toString() + ", 2) + power(Longitude__c - " + longitude.toString() + ", 2))) as distance from account where Latitude__c between " + Double.toString(latitude - degrees) + " and " + Double.toString(latitude + degrees) + " and Longitude__c between " + Double.toString(longitude - degrees) + " and " + Double.toString(longitude + degrees) + " order by distance limit " + Long.toString(limit) + ";");
		
		return processAccounts(result, latitude, longitude);
	}
}
