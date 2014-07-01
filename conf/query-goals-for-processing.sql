SELECT
	r.answer_text__c answer_text__c,
	q.answer_options__c answer_options__c,
	q.question_type__c question_type__c,
	q.sfid question__c,
	ag.sfid assigned_goal__c
FROM
	dms_survey_result__c r
	INNER JOIN dms_question__c     q  ON r.question__c = q.sfid
	INNER JOIN contact         	   c  ON c.sfid = r.contact__c
	INNER JOIN dd_assigned_goal__c ag ON ag.contact__c = c.sfid AND ag.dd_survey_question__c = q.sfid
WHERE
	(q.is_goal__c is not null AND q.is_goal__c = true) AND 
    r.dd_survey_submission__c__external_id__c = ''{0}''
ORDER BY
	question__c;
