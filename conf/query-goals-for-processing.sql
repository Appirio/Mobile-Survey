SELECT
	r.id id,
	r.answer_text__c answer_text__c,
	q.answer_options__c answer_options__c,
	q.question_type__c question_type__c,
	q.sfid question__c,
	ag.sfid assigned_goal__c,
	r.account__c account__c,
	r.goal_achievement__c goal_achievement__c
FROM
	dms_survey_result__c r
	INNER JOIN dms_question__c     q  ON r.question__c = q.sfid
	INNER JOIN dd_assigned_goal__c ag ON ag.contact__c = ''{1}'' AND ag.dd_survey_question__c = q.sfid
WHERE
	(q.is_goal__c is not null AND q.is_goal__c = true) AND
    q.sfid IN (SELECT question__c from dms_survey_result__c where dd_survey_submission__c__external_id__c = ''{0}'')
ORDER BY
	q.sfid;
