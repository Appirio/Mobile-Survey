SELECT 
    q.sfid sfid,
    q.question_text__c question_text__c,
    q.parent_question__c parent_question__c,
    q.name as name,
    q.question_type__c question_type__c,
    q.dms_survey__c dms_survey__c,
    q.Include_Photos__c Include_Photos__c,
    q.goal_type__c goal_type,
    ag.target__c target__c,
    ag.achieved__c achieved__c,
    ag.start_date__c start_date__c,
    ag.end_date__c end_date__c,
    ag.goal_name__c goal_name__c,
    ag.sfid assigned_goal__c
FROM 
    dms_question__c q
    LEFT OUTER JOIN dd_assigned_goal__c ag on (ag.dd_survey_question__c = q.sfid and ag.contact__c = ''{0}'') 
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