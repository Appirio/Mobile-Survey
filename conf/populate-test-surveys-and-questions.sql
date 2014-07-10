INSERT INTO contact (
	sfid
) VALUES (
	'1'
);
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
	survey_type__c,
	active__c
) VALUES (
	'test survey 1',
	'1',
	'10',
	't',
	'Product',
	true
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
	'child survey 2',
	'6',
	'10',
	'Product',
	'f',
	'5'	 
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c,
	isparent__c,
	parent_survey__c
) VALUES (
	'child survey 1',
	'7',
	'10',
	'Product',
	'f',
	'5'	 
);
INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI',
	'g1',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI UGX',
	'g2',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI NUGI',
	'g3',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI NUGX',
	'g4',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI UGX NUGI',
	'g5',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI UGX NUGX',
	'g6',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI NUGI NUGX',
	'g7',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGI UGX NUGI NUGX',
	'g8',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGX',
	'g9',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGX NUGI',
	'g10',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGX NUGX',
	'g11',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'UGX NUGI NUGX',
	'g12',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'NUGI',
	'g13',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'NUGI NUGX',
	'g14',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	survey_type__c
) VALUES (
	'NUGX',
	'g15',
	'10',
	'Product'
);
INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	universal_survey__c,
	survey_type__c,
	active__c
) VALUES (
	'test survey inactive',
	'10',
	'10',
	't',
	'Product',
	false
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c,
	max_goal_score__c,
	goal_type__c,
	goal_end_date__c,
	goal_name__c,
	is_goal__c,
	goal_start_date__c,
	max_score__c
) VALUES (
	'1',
	'the question text',
	null,
	'question 1',
	'Count',
	'1',
	10,
	'type',
	'2014-06-25',
	'the goal name',
	true,
	'2014-05-25',
	200
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
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'11',
	'the question text',
	null,
	'question 8',
	'Count',
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
	'g1',
	'the question text',
	null,
	'question 8',
	'Count',
	'g1'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g2',
	'the question text',
	null,
	'question 8',
	'Count',
	'g2'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g3',
	'the question text',
	null,
	'question 8',
	'Count',
	'g3'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g4',
	'the question text',
	null,
	'question 8',
	'Count',
	'g4'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g5',
	'the question text',
	null,
	'question 8',
	'Count',
	'g5'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g6',
	'the question text',
	null,
	'question 8',
	'Count',
	'g6'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g7',
	'the question text',
	null,
	'question 8',
	'Count',
	'g7'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g8',
	'the question text',
	null,
	'question 8',
	'Count',
	'g8'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g9',
	'the question text',
	null,
	'question 8',
	'Count',
	'g9'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g10',
	'the question text',
	null,
	'question 8',
	'Count',
	'g10'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g11',
	'the question text',
	null,
	'question 8',
	'Count',
	'g11'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g12',
	'the question text',
	null,
	'question 8',
	'Count',
	'g12'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g13',
	'the question text',
	null,
	'question 8',
	'Count',
	'g13'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g14',
	'the question text',
	null,
	'question 8',
	'Count',
	'g14'
);
INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'g15',
	'the question text',
	null,
	'question 8',
	'Count',
	'g15'
);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001qAuwIAE', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aQxYIAU', '[{"value":"Yes","score":"55","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-06-24 13:24:41', NULL, '2014-06-26 12:09:22', NULL, NULL, false, NULL, NULL, 'Select', 'Is Smirnoff in Distribution?', NULL, false, NULL, '2014-06-26 12:09:22', 'Q-1514', 6, 'a2iJ0000000NyZ6IAK', 1, 'Distribution', 55, false, '2014-06-01', true, 'Smirnoff distribution', '2014-06-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjkIAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 10:39:34', 'No', '2014-07-01 10:39:45', NULL, NULL, false, NULL, NULL, 'Select', 'What is question two NO ?', NULL, false, 'a2gJ0000000aRjaIAE', '2014-07-01 10:39:45', 'Q-1537', 24, 'a2iJ0000000O1VPIA0', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjfIAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 10:39:15', 'Yes', '2014-07-01 10:39:49', NULL, NULL, false, NULL, NULL, 'Select', 'What is question two YES?', NULL, false, 'a2gJ0000000aRjaIAE', '2014-07-01 10:39:49', 'Q-1536', 25, 'a2iJ0000000O1VPIA0', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjQIAU', '[{"value":"Yes","score":"0","goalScore":"10"},{"value":"No","score":"0","goalScore":"5"}]', '2014-07-01 10:37:05', NULL, '2014-07-01 10:40:37', NULL, NULL, false, NULL, 'Number type additional comments', 'Count', 'Complex question 1', 'a2gJ0000000aRjVIAU', false, NULL, '2014-07-01 10:40:37', 'Q-1533', 26, 'a2iJ0000000O1VPIA0', 10, 'Cold Box', 0, false, '2014-06-03', true, 'Complete 100', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjVIAU', '[{"value":"Yes","score":"0","goalScore":"2"},{"value":"No","score":"0","goalScore":"1"}]', '2014-07-01 10:37:47', NULL, '2014-07-01 10:41:25', NULL, NULL, false, NULL, 'Price type Additional cmments', 'Price', 'Complex question 2', 'a2gJ0000000aRjaIAE', false, NULL, '2014-07-01 10:41:25', 'Q-1534', 27, 'a2iJ0000000O1VPIA0', 2, 'Sampling', 0, false, '2014-06-26', true, 'Price goal', '2014-07-29', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjaIAE', '[{"value":"Yes","score":"10","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 10:38:20', NULL, '2014-07-01 10:43:29', NULL, NULL, false, NULL, NULL, 'Select', 'What is complex question?', 'a2gJ0000000aRjpIAE', false, NULL, '2014-07-01 10:43:29', 'Q-1535', 28, 'a2iJ0000000O1VPIA0', NULL, NULL, 10, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjuIAE', '[{"value":"Yes","score":"0","goalScore":"100"},{"value":"No","score":"0","goalScore":"10"}]', '2014-07-01 10:44:53', NULL, '2014-07-01 10:46:07', NULL, NULL, false, NULL, NULL, 'Text', 'Text Type questions', NULL, false, NULL, '2014-07-01 10:46:07', 'Q-1539', 30, 'a2iJ0000000O1VPIA0', 100, 'Ad Execution', 0, true, '2014-07-10', true, 'Text type goal', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRjpIAE', '[{"value":"Yes","score":"10","goalScore":"10"},{"value":"May be","score":"5","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 10:43:28', NULL, '2014-07-01 10:44:54', NULL, NULL, true, NULL, NULL, 'Multi-Select', 'My question about beer', 'a2gJ0000000aRjuIAE', false, NULL, '2014-07-01 10:44:54', 'Q-1538', 29, 'a2iJ0000000O1VPIA0', 11, 'Distribution', 15, true, '2014-07-01', true, 'Goal Name 111', '2014-07-24', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRk9IAE', '[{"value":"Captain Morgan 100 (Rum)","score":"1","goalScore":"10"},{"value":"Captain Morgan White (Rum)","score":"2","goalScore":"10"},{"value":"Captain Morgan Black Spiced (Rum)","score":"3","goalScore":"10"}]', '2014-07-01 10:56:24', 'Yes', '2014-07-01 11:00:31', NULL, NULL, false, NULL, NULL, 'Multi-Select', 'Enter quantity by brand', NULL, false, 'a2gJ0000000aRjzIAE', '2014-07-01 11:00:31', 'Q-1542', 31, 'a2iJ0000000O1VUIA0', 30, 'Display', 6, true, '2014-07-01', true, 'Enter quantity by brand', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRkOIAU', '[{"value":"Yes","score":"0","goalScore":"10"},{"value":"Partial","score":"0","goalScore":"10"},{"value":"No","score":"0","goalScore":"10"}]', '2014-07-01 11:19:16', NULL, '2014-07-01 11:21:24', NULL, NULL, false, NULL, NULL, 'Multi-Select', 'Is new promotion scheme decoration there ?', NULL, false, NULL, '2014-07-01 11:21:24', 'Q-1544', 34, 'a2iJ0000000O1VUIA0', 30, 'General', 0, true, '2014-07-16', true, 'Check decoration', '2014-07-23', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3z6IAA', NULL, 'a2gJ0000000aRkEIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"1"}]', '2014-07-01 11:00:48', NULL, '2014-07-01 11:19:17', NULL, NULL, false, NULL, NULL, 'Count', 'Count of bottles of XYZ?', 'a2gJ0000000aRkOIAU', false, NULL, '2014-07-01 11:19:17', 'Q-1543', 33, 'a2iJ0000000O1VUIA0', 1, 'QD / Volume', 0, false, '2014-07-01', true, 'Count of Bottles', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRkdIAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 13:10:50', 'Yes', '2014-07-01 13:10:59', NULL, NULL, false, NULL, NULL, 'Count', 'How many cases?', NULL, false, 'a2gJ0000000aRkYIAU', '2014-07-01 13:10:59', 'Q-1546', 35, 'a2iJ0000000O1YiIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aRkYIAU', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 13:09:01', NULL, '2014-07-01 18:42:30', NULL, NULL, false, NULL, NULL, 'Select', 'US1487 Photo - Is Captain Morgan Black Spiced Rum 1.0L on display?', 'a2gJ0000000aRkiIAE', false, NULL, '2014-07-01 18:42:30', 'Q-1545', 36, 'a2iJ0000000O1YiIAK', 0, 'Display', 0, true, '2014-07-01', true, 'Display - Captain Morgan', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001q3z6IAA', '005J0000001q3wMIAQ', NULL, 'a2gJ0000000aRjzIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"1"}]', '2014-07-01 10:54:22', NULL, '2014-07-02 16:51:46', NULL, NULL, false, NULL, NULL, 'Select', 'Was Captain Morgan on showcase?', 'a2gJ0000000aRkEIAU', false, NULL, '2014-07-02 16:51:46', 'Q-1540', 32, 'a2iJ0000000O1VUIA0', 1, 'Distribution', 0, true, '2014-07-01', true, 'Was Captain Morgan on showcase?', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001qAuwIAE', '005J0000001q3wMIAQ', NULL, 'a2gJ0000000aQxTIAU', '[{"value":"Yes","score":"50","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-06-24 13:23:59', NULL, '2014-07-02 16:59:29', NULL, NULL, false, NULL, NULL, 'Count', 'Is Captain Morgan on Display?', 'a2gJ0000000aQxYIAU', false, NULL, '2014-07-02 16:59:29', 'Q-1513', 5, 'a2iJ0000000NyZ6IAK', 1, 'Display', 50, true, '2014-06-01', true, 'Captain display goal', '2014-08-31', true);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRkiIAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 13:11:08', NULL, '2014-07-01 13:42:33', NULL, NULL, false, NULL, NULL, 'Select', 'Is Captain Morgan White Rum on display?', 'a2gJ0000000aRkxIAE', false, NULL, '2014-07-01 13:42:33', 'Q-1547', 37, 'a2iJ0000000O1YiIAK', 0, 'Display', 0, false, '2014-07-01', true, 'Display - Ciroc', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRksIAE', '[{"value":"Accepted","score":"0","goalScore":"0"},{"value":"Confirmed","score":"0","goalScore":"1"}]', '2014-07-01 13:16:25', NULL, '2014-07-01 14:36:27', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for Captain Morgan 100 Proof Spiced Rum?', NULL, false, NULL, '2014-07-01 14:36:27', 'Q-1549', 39, 'a2iJ0000000O1YnIAK', 1, 'Point of Distribution', 0, false, '2014-07-01', true, 'POD - Captain Morgan 100 Proof Spiced Rum', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRlHIAU', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 15:46:36', NULL, '2014-07-01 15:46:58', NULL, NULL, false, NULL, NULL, 'Select', 'Is the JW training complete?', NULL, false, NULL, '2014-07-01 15:46:58', 'Q-1551', 41, 'a2iJ0000000O1YsIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRkxIAE', '[{"value":"Captain Morgan White Rum","score":"0","goalScore":"0"},{"value":"Captain Morgan OSR","score":"0","goalScore":"1"},{"value":"Captain Morgan Black Spiced Rum","score":"0","goalScore":"0"}]', '2014-07-01 13:42:33', NULL, '2014-07-01 16:09:00', NULL, NULL, true, NULL, NULL, 'Multi-Select', 'US1365 Select All - Which other Captain Morgan''s are on display?', NULL, false, NULL, '2014-07-01 16:09:00', 'Q-1550', 40, 'a2iJ0000000O1YiIAK', 1, 'Display', 0, false, '2014-07-01', true, 'Display - Captain Morgan', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRpdIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 13:43:59', NULL, '2014-07-02 13:45:15', NULL, NULL, false, NULL, NULL, 'Select', 'Is Captain Morgan Black Spiced Rum in cold box?', NULL, false, NULL, '2014-07-02 13:45:15', 'Q-1552', 42, 'a2iJ0000000O2BhIAK', 1, 'Cold Box', 0, false, '2014-07-01', true, 'Cold Box - Captain Morgan', '2014-07-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001qAuwIAE', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aRq7IAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 14:51:31', 'Captain Morgan White Rum', '2014-07-02 14:51:31', NULL, NULL, false, NULL, NULL, 'Select', 'Enter Question Here', NULL, false, 'a2gJ0000000aRkxIAE', '2014-07-02 14:51:31', 'Q-1553', 43, 'a2iJ0000000O1YiIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001qAuwIAE', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aRqCIAU', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 14:51:53', 'Captain Morgan OSR', '2014-07-02 14:51:53', NULL, NULL, false, NULL, NULL, 'Select', 'Enter Question Here', NULL, false, 'a2gJ0000000aRkxIAE', '2014-07-02 14:51:53', 'Q-1554', 44, 'a2iJ0000000O1YiIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005J0000001qAuwIAE', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aRqHIAU', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 14:51:55', 'Captain Morgan Black Spiced Rum', '2014-07-02 14:52:38', NULL, NULL, false, NULL, NULL, 'Select', 'how many cases of captain morgan black spiced?', NULL, false, 'a2gJ0000000aRkxIAE', '2014-07-02 14:52:38', 'Q-1555', 45, 'a2iJ0000000O1YiIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005J0000001qAuwIAE', NULL, 'a2gJ0000000aRknIAE', '[{"value":"Yes","score":"0","goalScore":"0"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-01 13:12:24', 'Yes', '2014-07-02 14:46:59', NULL, NULL, false, NULL, NULL, 'Count', 'How many cases?', NULL, false, 'a2gJ0000000aRkiIAE', '2014-07-02 14:46:59', 'Q-1548', 38, 'a2iJ0000000O1YiIAK', NULL, NULL, 0, false, NULL, false, NULL, NULL, false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrFIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:29:17', NULL, '2014-07-02 17:30:04', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD of Ciroc Amaretto?', 'a2gJ0000000aRrKIAU', false, NULL, '2014-07-02 17:30:04', 'Q-1562', 52, 'a2iJ0000000O2ITIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Ciroc Amaretto', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrKIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:30:04', NULL, '2014-07-02 17:31:11', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for Crown Royal XO?', 'a2gJ0000000aRrPIAU', false, NULL, '2014-07-02 17:31:11', 'Q-1563', 53, 'a2iJ0000000O2ITIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Crown Royal XO', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrPIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:31:10', NULL, '2014-07-02 17:31:56', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get POD for Ketel One?', 'a2gJ0000000aRrUIAU', false, NULL, '2014-07-02 17:31:56', 'Q-1564', 54, 'a2iJ0000000O2ITIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Ketel One', '2014-08-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrUIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:31:53', NULL, '2014-07-02 17:33:03', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for Stark Raving Red?', NULL, false, NULL, '2014-07-02 17:33:03', 'Q-1565', 55, 'a2iJ0000000O2ITIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Stark Raving Red', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrZIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:33:45', NULL, '2014-07-02 17:34:32', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for Bulleit?', 'a2gJ0000000aRreIAE', false, NULL, '2014-07-02 17:34:32', 'Q-1566', 56, 'a2iJ0000000O2IYIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Bulleit', '2014-08-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRreIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:34:31', NULL, '2014-07-02 17:35:25', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for Crown Royal XO?', 'a2gJ0000000aRrjIAE', false, NULL, '2014-07-02 17:35:25', 'Q-1567', 57, 'a2iJ0000000O2IYIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - Crown Royal XO', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRqlIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:20:54', NULL, '2014-07-02 17:41:50', NULL, NULL, false, NULL, NULL, 'Select', 'Is Acacia Vineyard on display?', 'a2gJ0000000aRqqIAE', false, NULL, '2014-07-02 17:41:50', 'Q-1556', 46, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Acacia Vineyard', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrjIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:35:24', NULL, '2014-07-02 17:36:16', NULL, NULL, false, NULL, NULL, 'Select', 'Did you get a POD for George Dickel?', NULL, false, NULL, '2014-07-02 17:36:16', 'Q-1568', 58, 'a2iJ0000000O2IYIA0', 1, 'Point of Distribution', 0, false, '2014-06-01', true, 'POD - George Dickel', '2014-08-31', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRroIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:37:41', NULL, '2014-07-02 17:38:23', NULL, NULL, false, NULL, NULL, 'Select', 'Is there a feature for Ciroc?', 'a2gJ0000000aRrtIAE', false, NULL, '2014-07-02 17:38:23', 'Q-1569', 59, 'a2iJ0000000O2IdIAK', 1, 'Feature', 0, false, '2014-06-01', true, 'Feature - Ciroc', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRryIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:39:33', NULL, '2014-07-02 17:40:23', NULL, NULL, false, NULL, NULL, 'Select', 'Is there a feature for Crown Royal?', 'a2gJ0000000aRs3IAE', false, NULL, '2014-07-02 17:40:23', 'Q-1571', 61, 'a2iJ0000000O2IdIAK', 1, 'Feature', 0, false, '2014-06-01', true, 'Feature - Crown Royal', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRs3IAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:40:22', NULL, '2014-07-02 17:41:06', NULL, NULL, false, NULL, NULL, 'Select', 'Is there a feature for Crown Royal XO?', NULL, false, NULL, '2014-07-02 17:41:06', 'Q-1572', 62, 'a2iJ0000000O2IdIAK', 1, 'Feature', 0, false, '2014-06-01', true, 'Feature - Crown Royal XO', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrtIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:38:22', NULL, '2014-07-02 17:39:35', NULL, NULL, false, NULL, NULL, 'Select', 'Is there a feature for Johnnie Walker?', 'a2gJ0000000aRryIAE', false, NULL, '2014-07-02 17:39:35', 'Q-1570', 60, 'a2iJ0000000O2IdIAK', 1, 'Feature', 0, false, '2014-06-01', true, 'Feature - Johnnie Walker', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRqqIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:21:04', NULL, '2014-07-02 17:41:53', NULL, NULL, false, NULL, NULL, 'Select', 'Is Buchanan''s on display?', 'a2gJ0000000aRqvIAE', false, NULL, '2014-07-02 17:41:53', 'Q-1557', 47, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Buchanan''s', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRqvIAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:21:45', NULL, '2014-07-02 17:41:56', NULL, NULL, false, NULL, NULL, 'Select', 'Is Ciroc on display?', 'a2gJ0000000aRr0IAE', false, NULL, '2014-07-02 17:41:56', 'Q-1558', 48, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Ciroc', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRr0IAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:22:06', NULL, '2014-07-02 17:42:00', NULL, NULL, false, NULL, NULL, 'Select', 'Is Crown Royal on display?', 'a2gJ0000000aRr5IAE', false, NULL, '2014-07-02 17:42:00', 'Q-1559', 49, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Crown Royal', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRr5IAE', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:22:17', NULL, '2014-07-02 17:42:03', NULL, NULL, false, NULL, NULL, 'Select', 'Is Captain Morgan Caribbean White Rum on display?', 'a2gJ0000000aRrAIAU', false, NULL, '2014-07-02 17:42:03', 'Q-1560', 50, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Captain Morgan Caribbean White Rum', '2014-09-30', false);
INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c) VALUES ('005A0000002cSuMIAU', '005A0000002cSuMIAU', NULL, 'a2gJ0000000aRrAIAU', '[{"value":"Yes","score":"0","goalScore":"1"},{"value":"No","score":"0","goalScore":"0"}]', '2014-07-02 17:23:21', NULL, '2014-07-02 17:42:06', NULL, NULL, false, NULL, NULL, 'Select', 'Is Captain Morgan Original Spiced Rum on display?', NULL, false, NULL, '2014-07-02 17:42:06', 'Q-1561', 51, 'a2iJ0000000O2IJIA0', 1, 'Display', 0, true, '2014-06-01', true, 'Display - Captain Morgan Original Spiced Rum', '2014-09-30', false);
INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'UGI',
	'UGI'
);
INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'UGX',
	'UGX'
);
INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'NUGI',
	'NUGI'
);
INSERT INTO dd_survey_group__c (
	name, 
	sfid
) VALUES (
	'NUGX',
	'NUGX'
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
INSERT INTO dd_group_member__c (
	sfid,
	dd_survey_group__c,
	contact__c
) VALUES (
	'2',
	'UGX',
	'1'
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'1',
	'UGI',
	'g1',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'2',
	'UGI',
	'g2',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'3',
	'UGX',
	'g2',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'4',
	'UGI',
	'g3',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'5',
	'NUGI',
	'g3',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'6',
	'UGI',
	'g4',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'7',
	'NUGX',
	'g4',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'8',
	'UGI',
	'g5',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'9',
	'UGX',
	'g5',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'10',
	'NUGI',
	'g5',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'11',
	'UGI',
	'g6',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'12',
	'UGX',
	'g6',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'13',
	'NUGX',
	'g6',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'14',
	'UGI',
	'g7',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'15',
	'NUGI',
	'g7',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'16',
	'NUGX',
	'g7',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'17',
	'UGI',
	'g8',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'18',
	'UGX',
	'g8',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'19',
	'NUGI',
	'g8',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'20',
	'NUGX',
	'g8',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'21',
	'UGX',
	'g9',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'22',
	'UGX',
	'g10',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'23',
	'NUGI',
	'g10',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'24',
	'UGX',
	'g11',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'25',
	'NUGX',
	'g11',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'26',
	'UGX',
	'g12',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'27',
	'NUGI',
	'g12',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'28',
	'NUGX',
	'g12',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'29',
	'NUGI',
	'g13',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'30',
	'NUGI',
	'g14',
	false
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'31',
	'NUGX',
	'g14',
	true
);
INSERT INTO dd_survey_member__c (
	sfid,
	dd_survey_group__c,
	dd_survey__c,
	exclude__c
) VALUES (
	'32',
	'NUGX',
	'g15',
	true
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'1',
	'1',
	'a2gJ0000000aRqlIAE'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'2',
	'1',
	'a2gJ0000000aRqqIAE'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'3',
	'1',
	'a2gJ0000000aRqvIAE'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'4',
	'1',
	'a2gJ0000000aRr0IAE'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'5',
	'1',
	'a2gJ0000000aRr5IAE'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'6',
	'1',
	'a2gJ0000000aRrAIAU'
);
INSERT INTO dd_assigned_goal__c (
	sfid,
	contact__c, 
	dd_survey_question__c 
) VALUES (
	'7',
	'1',
	'a2gJ0000000aRjpIAE'
);