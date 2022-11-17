-- The mother is a one to many connection, patient one is the real patient and the patient two is the connected patient,
-- in our case the real patient's mother
UPDATE `patient` p1
INNER JOIN `patient_connection` conn ON conn.patient_one = p1.id
INNER JOIN `patient` p2 ON p2.id = conn.patient_two
SET p1.mother_name = CONCAT_WS(' ', p2.first_name, p2.last_name)
WHERE p1.mother_name IS NULL AND conn.type = 'mother'
