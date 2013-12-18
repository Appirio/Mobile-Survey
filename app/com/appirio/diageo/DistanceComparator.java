package com.appirio.diageo;

import java.util.Comparator;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DistanceComparator implements Comparator<ObjectNode> {

	@Override
	public int compare(ObjectNode obj1, ObjectNode obj2) {
		if((obj1.has("distance") && obj2.has("distance")) && !obj1.get("distance").asText().equals(obj2.get("distance").asText())) {
			return obj1.get("distance").asText().compareTo(obj2.get("distance").asText());
		}
		
		return obj1.get("id").asText().compareTo(obj2.get("id").asText());
	}
	
}

