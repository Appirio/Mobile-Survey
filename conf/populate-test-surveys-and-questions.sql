INSERT INTO	account (
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
	Active__c,
	tdlinx_sector__c
) values (
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
	 '1',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	universal_survey__c,
	survey_type__c
) VALUES (
	'test survey 1',
	'1',
	'10',
	't',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	grading_scale__c, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'test survey 2',
	'2',
	'1',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	grading_scale__c, 
	total_possible_score__c,
	sector__c,
	survey_type__c
) VALUES (
	'test survey 31',
	'3',
	'1',
	'10',
	'ON PREMISE',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	universal_survey__c,
	survey_type__c
) VALUES (
	'test survey 2',
	'4',
	'10',
	't',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c,
	isparent__c
) VALUES (
	'parent survey',
	'5',
	'10',
	'Product',
	't'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c,
	isparent__c,
	parent_survey__c
) VALUES (
	'child survey',
	'6',
	'10',
	'Product',
	'f',
	'5'	 
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'1',
	'the question text',
	null,
	'question 1',
	'Count',
	'1'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'2',
	'the question text',
	null,
	'question 2',
	'Count',
	'1'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'3',
	'the question text',
	null,
	'question 3',
	'Count',
	'1'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'4',
	'the question text',
	null,
	'question 4',
	'Count',
	'2'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'5',
	'the question text',
	null,
	'question 5',
	'Count',
	'2'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'6',
	'the question text',
	null,
	'question 6',
	'Count',
	'3'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'7',
	'the question text',
	null,
	'question 7',
	'Count',
	'4'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'8',
	'the question text',
	null,
	'question 8',
	'Count',
	'6'
);
