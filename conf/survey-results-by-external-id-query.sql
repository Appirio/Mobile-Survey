SELECT
	sfid,
	goal_achievement__c
FROM
	dms_survey_result__c
WHERE
	dd_survey_submission__c__external_id__c = ''{0}''