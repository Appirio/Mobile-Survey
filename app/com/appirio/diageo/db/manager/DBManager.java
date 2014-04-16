package com.appirio.diageo.db.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private static SimpleDateFormat formatDetail =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final boolean IS_TEST = System.getenv("IS_TEST") != null && System.getenv("IS_TEST").equalsIgnoreCase("true"); 
	
	private static Map<String, String> sqlMap = new HashMap<String, String>();
	
	public DBManager() throws DiageoServicesException {
		try {
			if(IS_TEST) {
				Class.forName("org.postgresql.Driver");
				db = DriverManager.getConnection("jdbc:postgresql://ec2-54-204-40-140.compute-1.amazonaws.com:5432/dfqm1m48t59q0b?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
						"zxzkfdezaucilp",
						"sN8-fSJnyXtOuudA53r4pbIx8o");
			} else {
				db = DB.getConnection();
			}
			
			mapper = new ObjectMapper();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DiageoServicesException();
		}
	}
	
	private void loadSQLStatement(String name) throws DiageoServicesException {
		try {
			InputStream in = AccountDBManager.class.getClassLoader()
			        .getResourceAsStream(name + ".sql");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line;
			StringBuilder result = new StringBuilder();
			
			while((line = reader.readLine()) != null) {
				result.append(line);
			}
			
			DBManager.sqlMap.put(name, result.toString()); 
		} catch (IOException e) {
			throw new DiageoServicesException(e);
		}
	}
	
	public String getSQLStatement(String name) throws DiageoServicesException {
		if(DBManager.sqlMap.get(name) == null) {
			loadSQLStatement(name);
		}
		
		return DBManager.sqlMap.get(name);
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
			
			System.out.println(query);
			
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
			
			return s.execute(statement);
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
	
	protected String dateToPostgresString(Date date, Boolean detailed) {
	    if (detailed) {
	        return formatDetail.format(date);
	    }
	    else {
	        return format.format(date);
	    }
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
