select 
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
  	(|/( power(Latitude__c - {0}, 2) + power(Longitude__c - {1}, 2))) as distance 
from 
	account 
where 
	sfid in (
		SELECT
			account__c
		FROM
			dd_my_accounts__c
		WHERE
			contact__c = ''{2}''
	) and Active__c = ''Yes''
order by 
	distance 