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
	 '1',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE',
	 ''
);

INSERT INTO contact (
	sfid
) VALUES (
	'2'
);

INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 '2',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE',
	 'Value1;Value2;Value3'
);

INSERT INTO contact (
	sfid
) VALUES (
	'3'
);
INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 '3',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE',
	 'ValueZ;ValueX'
);

INSERT INTO contact (
	sfid
) VALUES (
	'4'
);
INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 '4',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE',
	 'Value9'
);


INSERT INTO contact (
	sfid
) VALUES (
	'5'
);
INSERT INTO	account (
	sfid,  
	TDLinx_Outlet_Desc__c,  
	Name,
	Active__c,
	tdlinx_sector__c,
	Category__c
) values (
	 '5',
	 'My Favorite Liquor Store',
	 'My Favorite Liquor Store',
	 'Yes',
	 'ON PREMISE',
	 null
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
	'Value1'
);


INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 2',
	'g2',
	'10',
	'Product',
	'Value1;Value2'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 3',
	'g3',
	'10',
	'Product',
	'Value1;Value2;Value3'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 4',
	'g4',
	'10',
	'Product',
	'DifferentValue'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 5',
	'g5',
	'10',
	'Product',
	'DifferentValue;Value1'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 5',
	'g6',
	'10',
	'Product',
	''
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 7',
	'g7',
	'10',
	'Product',
	null
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 8',
	'g8',
	'10',
	'Product',
	'Value1'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 9',
	'g9',
	'10',
	'Product',
	'Value3'
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 10',
	'g10',
	'10',
	'Product',
	''
);

INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 11',
	'g11',
	'10',
	'Product',
	null
);


INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'UGI',
	'UGI'
);

INSERT INTO dd_group_member__c (
	sfid,
	dd_survey_group__c,
	contact__c
) VALUES (
	'1',
	'UGI',
	'1'
);

INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'1',
	'UGI',
	'g6'
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'2',
	'UGI',
	'g7'
);

INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'UGI22',
	'UGI22'
);

INSERT INTO dd_group_member__c (
	sfid,
	dd_survey_group__c,
	contact__c
) VALUES (
	'2',
	'UGI22',
	'4'
);

INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'3',
	'UGI22',
	'g8'
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'4',
	'UGI22',
	'g9'
);

INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'UGI33',
	'UGI33'
);

INSERT INTO dd_group_member__c (
	sfid,
	dd_survey_group__c,
	contact__c
) VALUES (
	'3',
	'UGI33',
	'5'
);

INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'5',
	'UGI33',
	'g10'
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c
) VALUES (
	'6',
	'UGI33',
	'g11'
);