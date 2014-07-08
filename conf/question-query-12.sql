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
    dms_survey__c IN (
    	SELECT sfid 
        	FROM   dms_survey__c 
        WHERE  
        	(active__c IS NULL OR active__c = true )
        	AND ( isparent__c IS NULL OR isparent__c = false ) 
            AND parent_survey__c IS NULL
			AND (
				universal_survey__c or
				sfid in (
					SELECT
						dd_survey__c
					FROM
						dd_survey_member__c
					WHERE
						dd_survey_group__c IN (
							SELECT
								dd_survey_group__c
							FROM
								dd_group_member__c
							WHERE
								contact__c = ''{0}''
						)
				) or (
					SELECT
						count(*)
					FROM
						dd_survey_member__c
					WHERE
						dd_survey__c = q.dms_survey__c
				) = 0
			)            
	) 
ORDER  BY 
    dms_survey__c 