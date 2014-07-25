INSERT INTO contact (
	sfid
) VALUES (
	'1'
);

INSERT INTO dms_survey__c (
	name, 
	sfid, 
	total_possible_score__c,
	universal_survey__c,
	survey_type__c,
	active__c
) VALUES (
	'universal survey 1',
	'1',
	'10',
	't',
	'Product',
	true
);

INSERT INTO Brand__c (
	sfid,
	name,
	Country__c,
	Level__c,
	Brand_Type__c
) VALUES (
	'100',
	'Brand 1',
	'USA',
	'L1',
	'BRAND'
);

INSERT INTO Brand__c (
	sfid,
	name,
	Country__c,
	Level__c,
	Brand_Type__c
) VALUES (
	'101',
	'Brand 1',
	'USA',
	'L1',
	'BRAND'
);

INSERT INTO Brand__c (
	sfid,
	name,
	Country__c,
	Level__c,
	Brand_Type__c
) VALUES (
	'102',
	'Brand 2',
	'USA',
	'L1',
	'BRAND'
);

INSERT INTO Brand__c (
	sfid,
	name,
	Country__c,
	Level__c,
	Brand_Type__c
) VALUES (
	'103',
	'Brand 3',
	'USA',
	'L1',
	'BRAND X'
);
 
INSERT INTO Brand__c (
	sfid,
	name,
	Country__c,
	Level__c,
	Brand_Type__c
) VALUES (
	'104',
	'Brand 4',
	'USA',
	'L2',
	'BRAND Y'
);


INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c)
VALUES ('005J0000001qAuwIAE',
        '005J0000001qAuwIAE',
        NULL,
        'a2gJ0000000aQxYIAU',
        '[{"value":"Brand 1","valueBrandId":"100","score":"55","goalScore":"1"},{"value":"Brand 2","valueBrandId":"101","score":"1","goalScore":"0"},{"value":"Brand 4","valueBrandId":"103","score":"1","goalScore":"0"}]',
        '2014-06-24 13:24:41',
        NULL,
        '2014-06-26 12:09:22',
        NULL,
        NULL,
        FALSE,
        NULL,
        NULL,
        'Multi-Select',
        'Is Smirnoff in Distribution?',
        'a2gJ0000000aRjkIAE',
        FALSE,
        NULL,
        '2014-06-26 12:09:22',
        'Q-1514',
        6,
        '1',
        1,
        'Distribution',
        55,
        FALSE,
        '2014-06-01',
        TRUE,
        'Smirnoff distribution',
        '2014-06-30',
        FALSE);


INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c)
VALUES ('005J0000001q3z6IAA',
        '005J0000001q3z6IAA',
        NULL,
        'a2gJ0000000aRjkIAE',
        '[{"value":"Brand 2","valueBrandId":"101","score":"55","goalScore":"1"},{"value":"Brand 3","valueBrandId":"102","score":"0","goalScore":"0"}]',
        '2014-07-01 10:39:34',
        'No',
        '2014-07-01 10:39:45',
        NULL,
        NULL,
        FALSE,
        NULL,
        NULL,
        'Select',
        'What is question two NO ?',
        NULL,
        FALSE,
        'a2gJ0000000aRjaIAE',
        '2014-07-01 10:39:45',
        'Q-1537',
        24,
        '1',
        NULL,
        NULL,
        0,
        FALSE,
        NULL,
        FALSE,
        NULL,
        NULL,
        FALSE);


INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c)
VALUES ('005J0000001q3z6IAA',
        '005J0000001q3z6IAA',
        NULL,
        'a2gJ0000000aRjfIAE',
        '[{"value":"Brand 4","valueBrandId":"103","score":"55","goalScore":"1"},{"value":"Brand 2","valueBrandId":"101","score":"0","goalScore":"0"}]',
        '2014-07-01 10:39:15',
        'Yes',
        '2014-07-01 10:39:49',
        NULL,
        NULL,
        FALSE,
        NULL,
        NULL,
        'Select',
        'What is question two YES?',
        NULL,
        FALSE,
        'a2gJ0000000aRjaIAE',
        '2014-07-01 10:39:49',
        'Q-1536',
        25,
        '1',
        NULL,
        NULL,
        0,
        FALSE,
        NULL,
        FALSE,
        NULL,
        NULL,
        FALSE);


INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c)
VALUES ('005J0000001q3z6IAA',
        '005J0000001q3z6IAA',
        NULL,
        'a2gJ0000000aRjQIAU',
        '[{"value":"Brand 3","valueBrandId":"102","score":"55","goalScore":"1"},{"value":"Brand 1","valueBrandId":"100","score":"55","goalScore":"1"},{"value":"Brand 2","valueBrandId":"101","score":"0","goalScore":"0"}]',
        '2014-07-01 10:37:05',
        NULL,
        '2014-07-01 10:40:37',
        NULL,
        NULL,
        FALSE,
        NULL,
        'Number type additional comments',
        'Count',
        'Complex question 1',
        'a2gJ0000000aRjVIAU',
        FALSE,
        NULL,
        '2014-07-01 10:40:37',
        'Q-1533',
        26,
        '1',
        10,
        'Cold Box',
        0,
        FALSE,
        '2014-06-03',
        TRUE,
        'Complete 100',
        '2014-07-31',
        FALSE);


INSERT INTO dms_question__c (createdbyid, lastmodifiedbyid, connectionsentid, sfid, answer_options__c, createddate, conditional_answer__c, systemmodstamp, question_number__c, connectionreceivedid, include_none_of_the_above__c, _c5_source, label_for_add_l_comments__c, question_type__c, question_text__c, next_question__c, isdeleted, parent_question__c, lastmodifieddate, name, id, dms_survey__c, max_goal_score__c, goal_type__c, max_score__c, include_photos__c, goal_start_date__c, is_goal__c, goal_name__c, goal_end_date__c, isactive__c)
VALUES ('005J0000001q3z6IAA',
        '005J0000001q3z6IAA',
        NULL,
        'a2gJ0000000aRjVIAU',
        '[{"value":"Yes","valueBrandId":"","score":"55","goalScore":"1"},{"value":"No","valueBrandId":"","score":"0","goalScore":"0"}]',
        '2014-07-01 10:37:47',
        NULL,
        '2014-07-01 10:41:25',
        NULL,
        NULL,
        FALSE,
        NULL,
        'Price type Additional cmments',
        'Price',
        'Complex question 2',
        'a2gJ0000000aRjaIAE',
        FALSE,
        NULL,
        '2014-07-01 10:41:25',
        'Q-1534',
        27,
        '1',
        2,
        'Sampling',
        0,
        FALSE,
        '2014-06-26',
        TRUE,
        'Price goal',
        '2014-07-29',
        FALSE);

