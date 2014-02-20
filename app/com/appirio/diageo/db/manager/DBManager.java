package com.appirio.diageo.db.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.db.DB;

import com.appirio.diageo.db.DiageoServicesException;

public class DBManager {

	protected Connection db;
	protected ObjectMapper mapper;
	private static SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
	
	public DBManager() throws DiageoServicesException {
		try {
			db = DB.getConnection();
			mapper = new ObjectMapper();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException();
		}
	}
	
	public void close() throws DiageoServicesException {
		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}
	}
	
	protected ArrayNode queryToJson(String query) throws DiageoServicesException {
		try {
			Statement s = db.createStatement();
			
			//System.out.println(query);
			
			return resultSetToJson(s.executeQuery(query));
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}
	}
	
	protected ArrayNode resultSetToJson(ResultSet rs) throws DiageoServicesException {
		try {
			ArrayNode result = mapper.createArrayNode();

			ResultSetMetaData rsMetaData = rs.getMetaData();

			while(rs.next()) {
				ObjectNode record = mapper.createObjectNode();
				
				for(int i = 1; i <= rsMetaData.getColumnCount(); i++) {
					record.put(rsMetaData.getColumnName(i), rs.getString(rsMetaData.getColumnName(i)));
				}
				
				result.add(record);
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DiageoServicesException(e);
		}
	}
	
	protected boolean executeStatement(String statement) throws DiageoServicesException {
		try {
			Statement s = db.createStatement();
			
			System.out.println(statement);
			
			return s.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DiageoServicesException(e);
		}
	}
	
	protected void insert(ObjectNode data, String table) throws DiageoServicesException {
		StringBuilder insertStatement = new StringBuilder("insert into ");
		StringBuilder valuesClause = new StringBuilder();
		
		insertStatement.append(table);
		insertStatement.append(" (");
		 
		Iterator<String> fields = data.fieldNames();
		
		String separator = "";
		
		while(fields.hasNext()) {
			String fieldName = fields.next();
			
			insertStatement.append(separator);
			insertStatement.append(fieldName);

			valuesClause.append(separator);
			valuesClause.append("'" + data.get(fieldName).asText().replaceAll("'", "''") + "'");
			
			separator = ",";
		}
		
		insertStatement.append(") values (");
		
		insertStatement.append(valuesClause.toString());

		insertStatement.append(")");

        this.executeStatement(insertStatement.toString());
	}
	
	protected String dateToPostgresString(Date date) {
		return format.format(date);
	}
	
	protected void clearTransientFields(JsonNode data, List<String> fieldNames) {
		if(data.isArray()) {
			for(JsonNode obj : data) {
				clearTransientFields(obj, fieldNames);
			}
		} else {
			ObjectNode node = (ObjectNode) data;
			
			Iterator<String> it = node.fieldNames();
			
			ArrayList<String> fieldsToClean = new ArrayList<String>();
			
			while(it.hasNext()) {
				String fieldName = it.next();
				
				if((!fieldNames.contains(fieldName.toUpperCase())) || node.get(fieldName).asText().equals("null")) {
					fieldsToClean.add(fieldName);
				}
			}
			
			for(String fieldName : fieldsToClean) {
				node.remove(fieldName);
			}
		}
	}
	
	protected ObjectNode queryToJsonObject(String string) throws DiageoServicesException {
		JsonNode result = queryToJson(string);
		
		if(result.size() > 0) {
			return (ObjectNode) result.get(0);
		}
		
		return null;
	}

	
 }
