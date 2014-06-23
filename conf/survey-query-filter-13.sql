SELECT
    name,
    sfid,
    survey_type__c,
    first_question__c,
    grading_scale__c,
    total_possible_score__c
FROM
    dms_survey__c 
WHERE
    (Active__c is null or Active__c = true) and 
    (IsParent__c is null or IsParent__c = false) and 
    parent_survey__c is null and 
    (
        universal_survey__c or 
        (
            (sector__c is null or sector__c = 'ALL' or sector__c = '' or sector__c like '%" + account.get("tdlinx_sector__c").asText() + "%')
            AND (trade_channel__c is null or trade_channel__c = 'ALL' or trade_channel__c = '' or trade_channel__c like '%" + account.get("tdlinx_trade_channel__c").asText() + "%')
            and (sub_channel__c is null or sub_channel__c = 'ALL' or sub_channel__c = '' or sub_channel__c like '%" + account.get("tdlinx_sub_channel__c").asText() + "%')
            and (state__c is null or state__c = '' or state__c like '%" + account.get("tdlinx_outlet_state__c").asText() + "%')
            and (zip_codes__c is null or zip_codes__c = '' or zip_codes__c like '%" + zip + "%')
            and (national_account__c is null or national_account__c = ''or national_account__c = '" + account.get("national_account_group__c").asText() + "')
            and (marketing_group__c is null or marketing_group__c = ''or marketing_group__c = '" + account.get("marketing_group__c").asText() + "')
            and (tdlinx_acct_level_e__c is null or tdlinx_acct_level_e__c = '' or tdlinx_acct_level_e__c = '" + account.get("tdlinx_account_level_e__c").asText() + "')
       )
   )
   
ORDER BY
    sfid