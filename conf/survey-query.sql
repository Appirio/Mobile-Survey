select 
	name, 
	sfid, 
	grading_scale__c, 
	total_possible_score__c,
	survey_type__c
from 
	dms_survey__c s
where 
	universal_survey__c and 
	survey_type__c != ''Non Product'' and 
	(Active__c is null or Active__c = true) and 
	(IsParent__c is null or IsParent__c = false) and 
	parent_survey__c is null
order by 
	sfid