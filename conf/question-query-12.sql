SELECT answer_options__c, 
       sfid, 
       question_text__c, 
       parent_question__c, 
       name, 
       sfid, 
       question_type__c, 
       dms_survey__c 
FROM   
    dms_question__c 
WHERE  
    dms_survey__c IN (SELECT sfid 
                FROM   dms_survey__c 
                WHERE  ( active__c IS NULL OR active__c = true ) 
                AND ( isparent__c IS NULL OR isparent__c = false ) 
                AND parent_survey__c IS NULL) 
ORDER  BY 
    dms_survey__c 