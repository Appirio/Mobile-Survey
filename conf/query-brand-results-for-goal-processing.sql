SELECT
	b.id,
	r.goal_achievement__c,
	q.is_goal__c,
	r.answer_text__c,
	b.answer__c
FROM
	dd_survey_result_brands__c b
	INNER JOIN dms_survey_result__c r ON  r.result_brand_ext_id__c = b.dms_survey_result__c__result_brand_ext_id__c
	INNER JOIN dms_question__c q ON q.sfid = r.question__c
WHERE
	r.id IN ({0}) 