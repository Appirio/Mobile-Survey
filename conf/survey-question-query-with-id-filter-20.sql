SELECT 
    q.Answer_Options__c Answer_Options__c,
	q.Conditional_Answer__c Conditional_Answer__c,
	q.ConnectionReceivedId ConnectionReceivedId,
	q.ConnectionSentId ConnectionSentId,
	q.CreatedById CreatedById,
	q.CreatedDate CreatedDate,
	q.DMS_Survey__c DMS_Survey__c,
	s.Start_Date__c Goal_End_Date__c,
	q.Goal_Name__c Goal_Name__c,
	s.Start_Date__c Goal_Start_Date__c,
	q.Goal_Type__c Goal_Type__c,
	q.sfId sfId,
	q.Include_None_of_the_Above__c Include_None_of_the_Above__c,
	q.Include_Photos__c Include_Photos__c,
	q.Is_Goal__c Is_Goal__c,
	q.IsDeleted IsDeleted,
	q.Label_for_Add_l_Comments__c Label_for_Add_l_Comments__c,
	q.LastModifiedById LastModifiedById,
	q.LastModifiedDate LastModifiedDate,
	q.Max_Goal_Score__c Max_Goal_Score__c,
	q.Max_Score__c Max_Score__c,
	q.Name as Name,
	q.NEXT_Question__c NEXT_Question__c,
	q.Parent_Question__c Parent_Question__c,
	q.Question_Number__c Question_Number__c,
	q.Question_Text__c Question_Text__c,
	q.Question_Type__c Question_Type__c,
	q.SystemModstamp SystemModstamp,
	s.sector__c sector__c,
	s.name survey_name__c
FROM 
    dms_question__c q
    INNER JOIN dms_survey__c s ON s.sfid =  q.dms_survey__c 
WHERE 
    q.sfid in ({0})
ORDER BY
	q.sfid;
