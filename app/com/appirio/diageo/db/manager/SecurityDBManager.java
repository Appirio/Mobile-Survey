package com.appirio.diageo.db.manager;

import com.appirio.diageo.db.DiageoServicesException;

public class SecurityDBManager extends DBManager {

	public SecurityDBManager() throws DiageoServicesException {
		super();
	}

	public void saveSignature(String signature) throws DiageoServicesException {
		this.executeStatement("insert into used_signatures (signature) values ('" + signature + "')");
	}
	
}
