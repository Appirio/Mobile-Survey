SELECT 
    ag.Achieved__c Achieved__c,
	ag.Broker_SAM__c Broker_SAM__c,
	ag.Contact__c Contact__c,
	ag.Contact_Email__c Contact_Email__c,
	ag.DD_Survey_Question__c DD_Survey_Question__c,
	ag.End_Date__c End_Date__c,
	ag.Goal_Name__c Goal_Name__c,
	ag.sfid sfid,
	ag.Name as Name,
	ag.Start_Date__c Start_Date__c,
	ag.Target__c Target__c
FROM
    DD_Assigned_Goal__c ag
WHERE 
    Contact__c=''{0}''
    AND 
	(
		(
			start_date__c <= ''{2}''
		) and (
			end_date__c >= ''{1}''
		) 
	)
ORDER BY 
	ag.DD_Survey_Question__c;