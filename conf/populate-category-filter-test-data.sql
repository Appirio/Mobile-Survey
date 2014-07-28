INSERT INTO contact (
	sfid
) VALUES (
	'1'
);

INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 'NOCATEGORY',
	 'Account with no category',
	 'Account with no category',
	 'Yes',
	 'ON PREMISE',
	 ''
);

INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 'WITHCATEGORY',
	 'Account wth category',
	 'Account with category',
	 'Yes',
	 'ON PREMISE',
	 'CAT1'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 1',
	'g1',
	'10',
	'Product',
	'CAT1'
);
