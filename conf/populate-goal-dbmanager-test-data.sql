INSERT INTO contact (
    sfid
) VALUES (
    '1'
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
    goal_name__c,
    is_goal__c,
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
    'the goal name',
    true,
    200
);
INSERT INTO dd_assigned_goal__c (
    contact__c, 
    dd_survey_question__c 
) VALUES (
    '1',
    '1'
);

INSERT INTO contact (
    sfid
) VALUES (
    '2'
);

INSERT INTO dms_survey__c (
    name, 
    sfid, 
    total_possible_score__c,
    universal_survey__c,
    survey_type__c,
    Active__c
) VALUES (
    'test GoalAchievement100',
    '100',
    '1010',
    't',
    'Product',
    null
);

INSERT INTO dms_question__c (
    sfid, 
    question_text__c, 
    parent_question__c, 
    name, 
    question_type__c, 
    dms_survey__c
) VALUES (
    '100',
    'the GoalAchievement100',
    null,
    'question 100',
    'Count',
    '100'
);

INSERT INTO dms_question__c (
    sfid, 
    question_text__c, 
    parent_question__c, 
    name, 
    question_type__c, 
    dms_survey__c
) VALUES (
    '101',
    'the GoalAchievement101',
    null,
    'question 101',
    'Count',
    '100'
);


INSERT INTO dms_question__c (
    sfid, 
    question_text__c, 
    parent_question__c, 
    name, 
    question_type__c, 
    dms_survey__c,
    Goal_Name__c,
    Goal_Type__c,
    Is_Goal__c
) VALUES (
    '102',
    'the GoalAchievement102',
    null,
    'question 102',
    'Count',
    '100',
    'GOAL NAME 102',
    'Distribution',
    true
);

INSERT INTO dms_question__c (
    sfid, 
    question_text__c, 
    parent_question__c, 
    name, 
    question_type__c, 
    dms_survey__c,
    Goal_Name__c,
    Goal_Type__c,
    Is_Goal__c
) VALUES (
    '103',
    'the GoalAchievement103',
    null,
    'question 103',
    'Count',
    '100',
    'GOAL NAME 103',
    'Distribution',
    true
);

INSERT INTO DD_Assigned_Goal__c (
    sfid, 
    Contact__c,
    Contact_Email__c,
    DD_Survey_Question__c,
    Goal_Name__c,
    Start_Date__c,
    End_Date__c,
    Target__c
) VALUES (
    '10',
    '2',
    'test@test.com',
    '100',
    'GOAL NAME 102',
    to_date('2014-11-15', 'YYYY-MM-DD'),
    to_date('2014-11-30', 'YYYY-MM-DD'),
    100
);


INSERT INTO DD_Assigned_Goal__c (
    sfid, 
    Contact__c,
    Contact_Email__c,
    DD_Survey_Question__c,
    Goal_Name__c,
    Start_Date__c,
    End_Date__c,
    Target__c
) VALUES (
    '11',
    '2',
    'test@test.com',
    '101',
    'GOAL NAME 103',
    to_date('2014-12-15', 'YYYY-MM-DD'),
    to_date('2014-12-30', 'YYYY-MM-DD'),
    50
);

INSERT INTO DD_Assigned_Goal__c (
    sfid, 
    Contact__c,
    Contact_Email__c,
    DD_Survey_Question__c,
    Goal_Name__c,
    Start_Date__c,
    End_Date__c,
    Target__c
) VALUES (
    '12',
    '2',
    'test@test.com',
    '102',
    'GOAL NAME 103',
    to_date('2014-12-15', 'YYYY-MM-DD'),
    to_date('2014-12-30', 'YYYY-MM-DD'),
    50
);

INSERT INTO DD_Assigned_Goal__c (
    sfid, 
    Contact__c,
    Contact_Email__c,
    DD_Survey_Question__c,
    Goal_Name__c,
    Start_Date__c,
    End_Date__c,
    Target__c
) VALUES (
    '13',
    '2',
    'test@test.com',
    '103',
    'GOAL NAME 103',
    to_date('2014-12-15', 'YYYY-MM-DD'),
    to_date('2014-12-30', 'YYYY-MM-DD'),
    50
);



INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1000',
    '2',
    '100',
    '100',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-11-15', 'YYYY-MM-DD')
);
    

INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1001',
    '2',
    '100',
    '101',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-12-15', 'YYYY-MM-DD')
);


INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1002',
    '2',
    '100',
    '102',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-12-15', 'YYYY-MM-DD')
);


INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1003',
    '2',
    '100',
    '103',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-12-15', 'YYYY-MM-DD')
);

INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1004',
    '2',
    '100',
    '103',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-12-15', 'YYYY-MM-DD')
);

INSERT INTO DMS_Survey_Result__c(
    sfid,
    Contact__c, 
    dms_survey__c, 
    Question__c,
    Question_Text__c,
    Question_Type__c, 
    Answer_Text__c,
    Answer_value__c,
    Possible_Answers__c,
    Selected_Answers__c, 
    Score__c, 
    Survey_Date__c
) VALUES (
    '1005',
    '2',
    '100',
    '103',
    'Question_Text',
    'Question_Type',
    'Answer_Text',
    9999,
    8888,
    1111,
    22,
    to_date('2014-12-15', 'YYYY-MM-DD')
);