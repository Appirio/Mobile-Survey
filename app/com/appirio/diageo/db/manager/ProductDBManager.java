package com.appirio.diageo.db.manager;

import com.fasterxml.jackson.databind.JsonNode;

import com.appirio.diageo.db.DiageoServicesException;

public class ProductDBManager extends DBManager {

	public ProductDBManager() throws DiageoServicesException {
		super();
	}

	public JsonNode getProduct(String upcCode) throws DiageoServicesException {
		String processedUpc = "";
		String upcCodeField = "upc_code_11_digit__c";
		
		if(upcCode.length() == 8) {
			StringBuilder sb = new StringBuilder(upcCode.substring(0, 3));
			sb.append("00000");
			sb.append(upcCode.substring(3, 6));
			processedUpc = sb.toString();
		} else if(upcCode.length() > 11 && upcCode.length() <= 12) {
			processedUpc = upcCode.substring(0, 11);
		} else if(upcCode.length() > 12) {
			processedUpc = upcCode;
			upcCodeField = "upc_code__c";
		}

		return queryToJson("select Nielsen_Brand__c, Nielsen_Brand_Variant__c, sfid, Nielsen_Size__c, upc_code__c from dms_nielson_product__c where " + upcCodeField + " = '" + processedUpc + "';");			
	}

}
