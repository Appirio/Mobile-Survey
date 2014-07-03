SELECT 
	p.photo_url__c photo_url__c
FROM 
	DD_Survey_Result_Photos__c p
	inner join dms_survey_result__c r on r.result_ext_id__c = p.dms_survey_result__c__result_ext_id__c
WHERE 
	r.dd_survey_submission__c__external_id__c = ''{0}'';