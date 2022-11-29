
INSERT INTO `3sem_exam`.`address` (`street_address`, `zipcode`) VALUES ('Nørgaardsvej 30', '2800');
INSERT INTO `3sem_exam`.`address` (`street_address`, `zipcode`) VALUES ('Nørgaardsvej 25', '2800');

INSERT INTO user
VALUES ('admin', 'personaltrainer@gmail.com', 'test123', 1);

INSERT INTO user
VALUES ('user', 'testuser@gmail.com', 'test123', 2);

INSERT INTO user_roles
VALUES ('admin', 'admin');
INSERT INTO user_roles
VALUES ('user', 'user');