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
	(|/( power(Latitude__c - {0}, 2) + power(Longitude__c - {1}, 2))) as distance 
FROM 
	account 
WHERE 
	Latitude__c between {2} and {3} and 
	Longitude__c between {4} and {5} and
	active__c = ''Yes''
ORDER BY
	distance 
LIMIT
	{6};