SET FOREIGN_KEY_CHECKS = 0;

truncate table pet;
truncate table store;

INSERT into store(`id`, `name`, `location`, `contact_no`)
VALUES (21, 'super store', 'nassarawa', 89884422335);

INSERT INTO pet(`id`, `name`, `color`, `breed`, `age`, `pet_sex`, `store_id`)
VALUES (31, 'jill', 'blue', 'parrot', 6, 'MALE', 21),
(32, 'jack', 'black', 'dog', 2, 'MALE', 21),
(33, 'blue', 'white', 'cat', 6, 'FEMALE', 21),
(34, 'sally', 'brown', 'rat', 6, 'FEMALE', 21),
(35, 'mill', 'grey', 'goat', 6, 'MALE', 21),
(36, 'ross', 'pink', 'rabbit', 6, 'MALE', 21),
(37, 'gem', 'yellow', 'cat', 6, 'FEMALE', 21);


SET FOREIGN_KEY_CHECKS = 1


