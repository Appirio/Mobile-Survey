SELECT
	q.max_goal_score__c max_goal_score__c,
	q.goal_type__c goal_type__c,
	q.goal_name__c goal_name__c,
	q.is_goal__c is_goal__c,
	q.goal_start_date__c goal_start_date__c,
	q.max_score__c max_score__c,
	s.sector__c sector,
	q.end_date__c goal_end_date__c,
	
FROM
	dd_assigned_goal__c ag
	INNER JOIN dms_survey__c s ON s.sfid = q.dms_survey__c
	INNER JOIN dms_question__c q ON ag.dd_survey_question__c = q.sfid  
WHERE
	(is_goal__c is not null and is_goal__c = true) and 
	ag.contact__c = ''{0}'' and
	(
		(
			s.start_date__c <= ''{2}''
		) or (
			s.end_date__c >= ''{1}''
		) 
	)