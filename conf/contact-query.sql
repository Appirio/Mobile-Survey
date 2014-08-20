SELECT
	sfid, 
	email, 
	assigned_goal_count__c
FROM
	contact 
where 
	sfid = ''{0}'';