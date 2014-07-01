SELECT 
    Answer_Options__c,
	Conditional_Answer__c,
	ConnectionReceivedId,
	ConnectionSentId,
	CreatedById,
	CreatedDate,
	DMS_Survey__c,
	Goal_End_Date__c,
	Goal_Name__c,
	Goal_Start_Date__c,
	Goal_Type__c,
	sfId,
	Include_None_of_the_Above__c,
	Include_Photos__c,
	Is_Goal__c,
	IsActive__c,
	IsDeleted,
	Label_for_Add_l_Comments__c,
	LastModifiedById,
	LastModifiedDate,
	Max_Goal_Score__c,
	Max_Score__c,
	Name,
	NEXT_Question__c,
	Parent_Question__c,
	Question_Number__c,
	Question_Text__c,
	Question_Type__c,
	SystemModstamp
FROM 
    dms_question__c 
WHERE 
    sfid in ({0});