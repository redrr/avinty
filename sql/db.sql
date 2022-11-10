------------
-- Tables
------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE IF NOT EXISTS `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `mother_name` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `gender` varchar(64) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `date_of_birth` date NOT NULL,
  `date_of_death` date DEFAULT NULL,
  `place_of_birth` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `postcode` int(4) NOT NULL,
  `city` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `street_name` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `house_number` varchar(11) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `email_address` varchar(255) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` int(11) NOT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

DROP TABLE IF EXISTS `patient_connection`;
CREATE TABLE IF NOT EXISTS `patient_connection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_one` int(11) NOT NULL,
  `patient_two` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `quality` varchar(255) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `distance` int(2) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` int(11) NOT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

----------------
-- Foreignkeys
----------------
ALTER TABLE `patient_connection` ADD FOREIGN KEY (`patient_one`) REFERENCES `patient` (`id`);
ALTER TABLE `patient_connection` ADD FOREIGN KEY (`patient_two`) REFERENCES `patient` (`id`);

--------------
-- Triggers
--------------
DROP TRIGGER IF EXISTS `date_of_birth_checker`;
DELIMITER $$
CREATE TRIGGER `date_of_birth_checker` BEFORE INSERT ON `patient_connection` FOR EACH ROW BEGIN
	-- variable declarations
   	DECLARE date_one DATE ;
    DECLARE date_two DATE ;
    DECLARE date_final DATE ;
	

   	SET date_one = (SELECT date_of_birth FROM patient WHERE id = NEW.patient_one) ;
    SET date_two = (SELECT date_of_birth FROM patient WHERE id = NEW.patient_two) ;
    IF (date_one < date_two) THEN 
    	SET date_final = date_one ;
    ELSE 
    	SET date_final = date_two ;
    END IF;

   	-- trigger code
    IF (date_final > NEW.start_date) THEN
   		SET NEW.start_date = date_final ;
    END IF;
END
$$
DELIMITER ;
COMMIT;
