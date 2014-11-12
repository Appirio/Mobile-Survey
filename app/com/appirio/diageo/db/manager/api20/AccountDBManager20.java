package com.appirio.diageo.db.manager.api20;

import java.text.MessageFormat;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.api17.AccountDBManager17;
import com.appirio.diageo.geolocation.HaversineCalculator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AccountDBManager20 extends AccountDBManager17 {

	private static final int ACCOUNT_LOCATION_LIMIT = Integer.parseInt(System.getenv("ACCOUNT_LOCATION_LIMIT"));
	protected String contactId;
	
	public AccountDBManager20(String contactId) throws DiageoServicesException {
		super();
		
		this.contactId = contactId;
	}

	public JsonNode findAccounts(Double latitude, Double longitude, Double radius) throws DiageoServicesException {
		double degrees = radius / 50;
		
		ArrayNode result = queryToJson(MessageFormat.format(getSQLStatement("accounts-query-20"), latitude, longitude, latitude - degrees, latitude + degrees, longitude - degrees, longitude + degrees, ACCOUNT_LOCATION_LIMIT));
		
		return processAccounts(result, latitude, longitude);
	}
	
	public ObjectNode getAccount(String id) throws DiageoServicesException {
		return queryToJsonObject(MessageFormat.format(getSQLStatement("account-query-20"), id));
	}
	
	protected ArrayNode processAccounts(ArrayNode result, Double latitude, Double longitude) {
		for(JsonNode item : result) {
			ObjectNode obj = (ObjectNode) item;
			
			obj.put("distance", HaversineCalculator.calculateHaversine(obj.get("latitude__c").asDouble(),
					   obj.get("longitude__c").asDouble(),
					   latitude, longitude));
			
			obj.remove("latitude__c");
			obj.remove("longitude__c"); 
		}
		
		return result;
	}
	
	public JsonNode findAccounts(String surveyId, Double latitude, Double longitude, Double radius) throws DiageoServicesException {
		double degrees = radius / 50;
		ObjectNode survey = null;
		ArrayNode surveys = null;
		
		// get the survey
		SurveyDBManager20 surveyDBManager = new SurveyDBManager20(this.contactId);
		
		try {
			surveys = surveyDBManager.getSurvey(surveyId);
			
			if(surveys.size() == 0) {
				throw new DiageoServicesException("Invalid survey ID: " + surveyId);
			} else {
				survey = (ObjectNode) surveys.get(0);
			}
		} finally {
			surveyDBManager.close();
		}
		
		if(survey.get("universal_survey__c").asBoolean()) {
			return findAccounts(latitude, longitude, radius);
		} else {
			
			
			String levelEFilter = "";
			String levelEAll = "";
			
			if(!survey.has("tdlinx_account_level_e__c") || survey.get("tdlinx_account_level_e__c").asText().length() == 0 || survey.get("tdlinx_account_level_e__c").asText().equals("null")) {
				levelEAll = "ALL";
			} else { 
				levelEFilter = survey.get("tdlinx_account_level_e__c").asText();
			}

			String mktGroupFilter = "";
			String mktGroupAll = "";
			
			if(!survey.has("marketing_group__c") || survey.get("marketing_group__c").asText().length() == 0 || survey.get("marketing_group__c").asText().equals("null")) {
				mktGroupAll = "ALL";
			} else { 
				mktGroupFilter = survey.get("marketing_group__c").asText();
			}
			
			String accGroupFilter = "";
			String accGroupAll = "";

			if(!survey.has("national_account__c") || survey.get("national_account__c").asText().length() == 0 || survey.get("national_account__c").asText().equals("null")) {
				accGroupAll = "ALL";
			} else { 
				accGroupFilter = survey.get("national_account__c").asText();
			}
			
			String zipFilter = "";
			String zipAll = "";
			
			if(!survey.has("zip_codes__c") || survey.get("zip_codes__c").asText().length() == 0 || survey.get("zip_codes__c").asText().equals("null")) {
				zipAll = "ALL";
			} else { 
				zipFilter = survey.get("zip_codes__c").asText();
			}
			
			String segFilter = "";
			String segAll = "";
			
			if(!survey.has("account_segmentation__c") || survey.get("account_segmentation__c").asText().length() == 0 || survey.get("account_segmentation__c").asText().equals("null")) {
				segAll = "ALL";
			} else { 
				segFilter = survey.get("account_segmentation__c").asText();
			}
			
			String stateFilter = "''";
			String stateAll = "";

			if(!survey.has("state__c") || survey.get("state__c").asText().length() == 0 || survey.get("state__c").asText().equals("ALL") || survey.get("state__c").asText().equals("null")) {
				stateAll = "ALL";
			} else { 
				stateFilter = stringToInClause(survey.get("state__c").asText(), ";");
			}
			
			String subChannelFilter = "''";
			String subChannelAll = "";
			
			if(!survey.has("sub_channel__c") || survey.get("sub_channel__c").asText().length() == 0 || survey.get("sub_channel__c").asText().equals("ALL") || survey.get("sub_channel__c").asText().equals("null")) {
				subChannelAll = "ALL";
			} else { 
				subChannelFilter = stringToInClause(survey.get("sub_channel__c").asText(), ";");
			}
			
			String channelFilter = "''";
			String channelAll = "";
			
			if(!survey.has("trade_channel__c") || survey.get("trade_channel__c").asText().length() == 0 || survey.get("trade_channel__c").asText().equals("ALL") || survey.get("trade_channel__c").asText().equals("null")) {
				channelAll = "ALL";
			} else { 
				channelFilter = stringToInClause(survey.get("trade_channel__c").asText(), ";");
			}

			String sectorFilter = "''";
			String sectorAll = "";
			
			if(!survey.has("sector__c") || survey.get("sector__c").asText().length() == 0 || survey.get("sector__c").asText().equals("ALL") || survey.get("sector__c").asText().equals("null")) {
				sectorAll = "ALL";
			} else { 
				sectorFilter = stringToInClause(survey.get("sector__c").asText(), ";");
			}
			
			String categoryFilter = "'ALL' = 'ALL'";
			
			if(survey.has("category__c") && survey.get("category__c").asText().length() != 0 && !survey.get("category__c").asText().equals("ALL") || survey.get("category__c").asText().equals("null")) {
				for(String category : survey.get("category__c").asText().split(";")) {
					categoryFilter += " OR category__c like '%" + category + "%'";
				}
			} 
			
			ObjectNode result = mapper.createObjectNode();
			
			result.put("myAccounts", mapper.createArrayNode());
			result.put("proximityAccounts", queryToJson(MessageFormat.format(getSQLStatement("accounts-available-survey"),
					latitude, 
					longitude, 
					latitude - degrees, 
					latitude + degrees, 
					longitude - degrees, 
					longitude + degrees,
					sectorAll,
					sectorFilter,
					channelAll,
					channelFilter,
					subChannelAll,
					subChannelFilter,
					stateAll,
					stateFilter,
					segAll,
					segFilter,
					zipAll,
					zipFilter,
					accGroupAll,
					accGroupFilter,
					mktGroupAll,
					mktGroupFilter,
					levelEAll,
					levelEFilter,
					categoryFilter,
					ACCOUNT_LOCATION_LIMIT,
					this.contactId)));
			
			return result;
		}		
	}
	
	protected String stringToInClause(String input, String separator) {
		if(input.length() == 0) {
			return "''";
		}
		
		String sep = "";
		StringBuilder result = new StringBuilder();
		
		for(String token : input.split(";")) {
			result.append(sep);
			sep = ",";
			result.append("'");
			result.append(token);
			result.append("'");
		}
		
		return result.toString();
		
	}
	
}
