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
    INNER JOIN dms_question__c q ON ag.dd_survey_question__c = q.sfid 
    INNER JOIN dms_survey__c s ON s.sfid = q.dms_survey__c
WHERE 
	s.active__c = true 
	and (s.parent_survey__c = '''' or s.parent_survey__c = null or (select count(*) from dms_survey__c p where p.Active__c = true and p.sfid = s.parent_survey__c) >0 )
	and Contact__c=''{0}''
    AND 
	(
		(ag.start_date__c is null OR ag.start_date__c <= ''{2}'')
		and
		(ag.end_date__c is null OR ag.end_date__c >=  ''{1}'')
	)
ORDER BY 
	ag.DD_Survey_Question__c;
	
	
	