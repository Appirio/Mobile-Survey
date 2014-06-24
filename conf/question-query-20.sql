SELECT 
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
    dms_survey__c in 
        (SELECT
            sfid 
         FROM
            dms_survey__c 
         WHERE 
            universal_survey__c 
            and survey_type__c != ''Non Product'' 
            and (Active__c is null or Active__c = true) 
            and (IsParent__c is null or IsParent__c = false) 
            and parent_survey__c is null 
        ) 
ORDER BY
    dms_survey__c