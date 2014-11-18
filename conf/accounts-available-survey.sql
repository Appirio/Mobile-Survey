SELECT 
  latitude__c, 
  longitude__c, 
  tdlinx_outlet_city__c,  
  tdlinx_outlet_zip_code__c,  
  tdlinx_outlet_state__c,  
  tdlinx_outlet_addr__c,  
  billingstate,  
  billingcountry,  
  billingpostalcode, 
  billingcity,  
  billingstreet,  
  sfid,  
  TDLinx_Outlet_Desc__c,  
  Name,  
  TDLinx_Outlet_State__c,  
  (|/( power(Latitude__c - {0}, 2) + power(Longitude__c - {1}, 2))) as distance,
  category__c
FROM
  account
WHERE
  (
  	sfid in (
		SELECT
			account__c
		FROM
			dd_my_accounts__c
		WHERE
			contact__c = ''{26}''
  	)
  )
  AND active__c = ''Yes''
  AND (
  	(''ALL'' = ''{6}'' OR tdlinx_sector__c in ({7})) 
  	AND (''ALL'' = ''{8}'' OR tdlinx_trade_channel__c in ({9})) 
  	AND (''ALL'' = ''{10}'' OR tdlinx_sub_channel__c in ({11})) 
  	AND (''ALL'' = ''{12}'' OR tdlinx_outlet_state__c in ({13}))
  	AND (''ALL'' = ''{14}'' OR account_segmentatiobn__c = ''{15}'')
  	AND (''ALL'' = ''{16}'' OR tdlinx_outlet_zip_code__c = ''{17}'')
  	AND (''ALL'' = ''{18}'' OR national_account_group__c = ''{19}'')
	AND (''ALL'' = ''{20}'' OR marketing_group__c = ''{21}'')
	AND (''ALL'' = ''{22}'' OR tdlinx_account_level_e__c = ''{23}'')
	AND ({24})
  )
  AND 
  	( sfid in (SELECT DISTINCT dd_survey__c FROM dd_survey_member__c WHERE dd_survey_group__c IN 
                                    (SELECT sm.dd_survey_group__c 
                                     FROM dd_group_member__c gm 
                                     INNER JOIN dd_survey_member__c sm ON sm.dd_survey_group__c = gm.dd_survey_group__c 
                                     WHERE gm.contact__c = ''{26}''
                                    )
                     ) 
          OR (SELECT count(*) FROM dd_survey_member__c WHERE dd_survey__c = s.sfid) = 0
    )
    
ORDER BY
  distance 
LIMIT
 {25};