SELECT  
    isparent__c,
    parent_survey__c,
    enable_edit_on_review_screen__c,
    name,
    sfid,
    survey_type__c,
    first_question__c,
    grading_scale__c,
    total_possible_score__c,
    universal_survey__c,
    sector__c,
    tdlinx_acct_level_e__c,
    marketing_group__c,
    national_account__c,
    account_segmentation__c,
    trade_channel__c,
    sub_channel__c,
    state__c,
    sector__c,
    category__c
FROM
    dms_survey__c s
WHERE
	sfid = ''{0}''