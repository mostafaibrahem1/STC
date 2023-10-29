SELECT usr.user_id, usr.username, td.training_id, td.training_date, COUNT(*) as count
FROM User usr
    JOIN Training_details td ON usr.user_id = td.user_id
WHERE td.training_date IN (
    SELECT training_date
    FROM Training_details
    GROUP BY training_date
    HAVING COUNT(DISTINCT user_id) > 1
    )
GROUP BY usr.user_id, usr.username, td.training_id, td.training_date
ORDER BY td.training_date DESC;