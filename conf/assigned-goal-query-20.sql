SELECT 
    Achieved__c,
	Broker_SAM__c,
	ConnectionReceivedId,
	ConnectionSentId,
	Contact__c,
	Contact_Email__c,
	CreatedById,
	CreatedDate,
	DD_Survey_Question__c,
	End_Date__c,
	Goal_Name__c,
	sfid,
	IsDeleted,
	LastModifiedById,
	LastModifiedDate,
	LastReferencedDate,
	LastViewedDate,
	Name,
	Start_Date__c,
	SystemModstamp,
	Target__c
FROM
    DD_Assigned_Goal__c
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
