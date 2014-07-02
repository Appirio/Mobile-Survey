SELECT 
    ag.Achieved__c Achieved__c,
	ag.Broker_SAM__c Broker_SAM__c,
	ag.ConnectionReceivedId ConnectionReceivedId,
	ag.ConnectionSentId ConnectionSentId,
	ag.Contact__c Contact__c,
	ag.Contact_Email__c Contact_Email__c,
	ag.CreatedById CreatedById,
	ag.CreatedDate CreatedDate,
	ag.DD_Survey_Question__c DD_Survey_Question__c,
	ag.End_Date__c End_Date__c,
	ag.Goal_Name__c Goal_Name__c,
	ag.sfid sfid,
	ag.IsDeleted IsDeleted,
	ag.LastModifiedById LastModifiedById,
	ag.LastModifiedDate LastModifiedDate,
	ag.LastReferencedDate LastReferencedDate,
	ag.LastViewedDate LastViewedDate,
	ag.Name as Name,
	ag.Start_Date__c Start_Date__c,
	ag.SystemModstamp SystemModstamp,
	ag.Target__c Target__c
FROM
    DD_Assigned_Goal__c ag
WHERE 
    Contact__c=''{0}''
    AND 
    (
	    (
	        Start_Date__c <= ''{1}'' and End_Date__c >= ''{1}''
	    ) or (
	        Start_Date__c <= ''{2}'' and End_Date__c >= ''{2}''
	    ) 
    )
