SELECT
    name,
    sfid,
    grading_scale__c,
    total_possible_score__c
FROM 
    dms_survey__c s
WHERE 
    (
    	(Active__c is null or Active__c = true)    
    	and (IsParent__c is null or IsParent__c = false) 
    	and parent_survey__c is null
    	and start_date__c <= current_date 
    	and current_date <= end_date__c
    ) and universal_survey__c
    or (
        sfid in (
          SELECT
              DISTINCT dd_survey__c
          FROM
              dd_survey_member__c
          WHERE
              dd_survey_group__c IN (
                  SELECT
                      sm.dd_survey_group__c
                  FROM
                      dd_group_member__c gm
                      INNER JOIN dd_survey_member__c sm ON sm.dd_survey_group__c = gm.dd_survey_group__c
                  WHERE
                      gm.contact__c = ''{0}''
              ) 
        )
    )
ORDER BY
    sfid