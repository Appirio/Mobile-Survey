INSERT INTO	account (
	sfid, 
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
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c
) values (
	'001J000001NIHRPIA5',
	 10.0001,
	 10.0001,
	 'Chicago',
	 '60601',
	 'IL',
	 '123 main street',
	 'IL',
	 'US',
	 '60601',
	 'Chicago',
	 '123 main street',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes'
);

INSERT INTO contact (
	sfid
) VALUES (
	'003J000000x2t10IAA'
);

INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('93', 'Captain Morgan White Rum','USA',null, '3','Individual Variant' ,'L5', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );
INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('91', 'Cape Floral Chenin Blanc 0.75L 12x01 STANDARD','USA',null, '3','Configuration & Container' ,'L6', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );
INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('92', 'Cape Floral Chenin Blanc 0.75L','USA',null, '3','Volume' ,'L6', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );

INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c,
	dms_survey__c,
	goal_brand__c,
	is_goal__c,
	answer_options__c
) VALUES (
	'a2gJ0000000DAx3IAG',
	'question 1 - case2',
	null,
	'Q-1957',
	'Select',
	'a2iJ0000000AAlYIAW',
	'93',
	't',
	'[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]'
);

INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c,
	dms_survey__c,
	is_goal__c,
	answer_options__c
) VALUES (
	'a2gJ0000000DGsjIAG',
	'conditional question',
	'a2gJ0000000DAx3IAG',
	'Q-1959',
	'Select',
	'a2iJ0000000AAlYIAW',
	't',
	'[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c,
	start_date__c,
	end_date__c,
	first_question__c
	
) VALUES (
	'test survey US1612',
	'a2iJ0000000AAlYIAW',
	'0',
	'Non Product',
	'Value1',
	to_date('2014-07-29', 'YYYY-MM-DD'),
	to_date('2014-11-29', 'YYYY-MM-DD'),
	'a2gJ0000000DAx3IAG'
);

