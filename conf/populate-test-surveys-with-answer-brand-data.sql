INSERT INTO contact (
	sfid
) VALUES (
	'005J0000001q3z6IAA'
);


INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('91', 'Cape Floral Chenin Blanc 0.75L 12x01 STANDARD','USA',null, '3','Configuration & Container' ,'L6', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );
INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('92', 'Cape Floral Chenin Blanc 0.75L','USA',null, '3','Volume' ,'L6', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );
INSERT INTO brand__c (Id, Name,Country__c, Parent_Brand__c,Brand_Priority__c,Brand_Type__c, Level__c,IsDeleted,CreatedDate,LastModifiedDate) VALUES ('93', 'Captain Morgan White Rum','USA',null, '3','Individual Variant' ,'L5', false,'2014-07-09 04:03:36','2014-07-09 04:01:11' );



INSERT INTO dms_survey__c (
	name, 
	sfid,
	total_possible_score__c,
	survey_type__c,
	category__c
) VALUES (
	'Survey 1',
	'a2iJ0000000AAlYIAW',
	'10',
	'Product',
	'Value1'
);

INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'a2gJ0000000DAx3IAG',
	'the question text',
	null,
	'question 2',
	'Count',
	'a2iJ0000000AAlYIAW'
);

INSERT INTO dms_question__c (
	sfid, 
	question_text__c, 
	parent_question__c, 
	name, 
	question_type__c, 
	dms_survey__c
) VALUES (
	'a2gJ0000000DAx8IAG',
	'the question text',
	null,
	'question 2',
	'Count',
	'a2iJ0000000AAlYIAW'
);


