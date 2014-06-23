package com.appirio.diageo.db.manager.api15;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.ProductDBManager;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductDBManager15 extends ProductDBManager {

	public ProductDBManager15() throws DiageoServicesException {
		super();
	}

	public JsonNode getProducts() throws DiageoServicesException {
		return queryToJson(getSQLStatement("product-query-15"));
	}
}
