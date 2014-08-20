select 
	answer_options__c, 
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	sfid, 
	question_type__c, 
	dms_survey__c 
from 
	dms_question__c 
where 
	dms_survey__c in ({0})
order by 
	dms_survey__c