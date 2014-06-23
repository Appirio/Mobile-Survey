SELECT 
	isparent__c, 
	parent_survey__c, 
	enable_edit_on_review_screen__c, 
	name, 
	sfid, 
	survey_type__c, 
	first_question__c, 
	grading_scale__c, 
	total_possible_score__c 
FROM 
	dms_survey__c 
WHERE
    (Active__c is null or Active__c = true)
ORDER BY
	sfid