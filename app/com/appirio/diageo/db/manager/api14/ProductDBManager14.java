package com.appirio.diageo.db.manager.api14;

import com.appirio.diageo.db.DiageoServicesException;
import com.appirio.diageo.db.manager.ProductDBManager;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductDBManager14 extends ProductDBManager {

	public ProductDBManager14() throws DiageoServicesException {
		super();
	}

	public JsonNode getProducts() throws DiageoServicesException {
		return queryToJson(getSQLStatement("product-query-14"));
	}
}
