package com.appirio.mobilesurvey.db.manager;

import com.appirio.diageo.db.MSServicesException;

public class SecurityDBManager extends DBManager {

	public SecurityDBManager() throws MSServicesException {
		super();
	}

	public void saveSignature(String signature) throws MSServicesException {
		this.executeStatement("insert into used_signatures (signature) values ('" + signature + "')");
	}
	
}
