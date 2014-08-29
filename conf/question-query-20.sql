SELECT 
    q.include_none_of_the_above__c include_none_of_the_above__c,
    q.conditional_answer__c conditional_answer__c,
    q.next_question__c next_question__c,
    q.label_for_add_l_comments__c label_for_add_l_comments__c,
    q.answer_options__c answer_options__c,
    q.sfid sfid,
    q.question_text__c question_text__c,
    q.parent_question__c parent_question__c,
    q.name as name,
    q.question_type__c question_type__c,
    q.dms_survey__c dms_survey__c,
    q.Include_Photos__c Include_Photos__c,
    q.goal_type__c goal_type,
    s.start_date__c start_date__c,
    s.end_date__c end_date__c,
    q.Is_Goal__c Is_Goal__c,
    ag.target__c target__c,
    ag.achieved__c achieved__c,
    ag.goal_name__c goal_name__c,
    ag.sfid assigned_goal__c,
    s.sector__c
FROM 
    dms_question__c q
    INNER JOIN dms_survey__c s ON s.sfid = q.dms_survey__c
    LEFT OUTER JOIN dd_assigned_goal__c ag on (ag.dd_survey_question__c = q.sfid and ag.contact__c = ''{0}'') 
WHERE 
    dms_survey__c in ({1})
ORDER BY
    dms_survey__c