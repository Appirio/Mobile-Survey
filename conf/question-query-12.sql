SELECT answer_options__c, 
       sfid, 
       question_text__c, 
       parent_question__c, 
       name, 
       sfid, 
       question_type__c, 
       dms_survey__c 
FROM   
    dms_question__c q
WHERE  
	dms_survey__c in ({0}) 
ORDER  BY 
    dms_survey__c 