SELECT 
    include_none_of_the_above__c,
    conditional_answer__c,
    next_question__c,
    label_for_add_l_comments__c,
    answer_options__c,
    sfid,
    question_text__c,
    parent_question__c,
    name,
    sfid,
    question_type__c,
    dms_survey__c
FROM 
    dms_question__c 
ORDER BY
    dms_survey__c