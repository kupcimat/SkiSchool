INSERT INTO person(id, dtype, lastname, phone, birthnumber, email, address, firstname, disabled)
    VALUES (1111110, 'instructor', 'simpson', '12345678', '12345682', 'homer@fox.com', 'evergreen terace', 'homer', FALSE);
INSERT INTO person(id, dtype, lastname, phone, birthnumber, email, address, firstname, disabled)
    VALUES (1111111, 'instructor', 'simpson', '87654321', '76655443', 'marge@fox.com', 'evergreen terace', 'marge', FALSE);
INSERT INTO person(id, dtype, lastname, phone, birthnumber, email, address, firstname, disabled)
    VALUES (1111112, 'instructor', 'simpson', '99653345', '98667744', 'abe@fox.com', 'retirement home', 'abe', FALSE);
INSERT INTO person(id, dtype, lastname, phone, birthnumber, email, address, firstname, disabled)
    VALUES (1111113, 'student', 'simpson', '78795655', '67678999', 'bart@fox.com', 'evergreen terace', 'bart', FALSE);
INSERT INTO person(id, dtype, lastname, phone, birthnumber, email, address, firstname, disabled)
    VALUES (1111114, 'student', 'simpson', '09567870', '90095657', 'lisa@fox.com', 'evergreen terace', 'lisa', FALSE);

INSERT INTO instructor(id, sex, positionsnowboard, seasonhours, active, positionski, totalhours, note, bankaccount)
    VALUES (1111110, 'Male', 'unknown', 12, TRUE, 'unknown', 34, 'doh', '90903356/7688');
INSERT INTO instructor(id, sex, positionsnowboard, seasonhours, active, positionski, totalhours, note, bankaccount)
    VALUES (1111111, 'Female', 'unknown', 45, TRUE, 'unknown', 78, 'loves cooking', '87348800/7688');
INSERT INTO instructor(id, sex, positionsnowboard, seasonhours, active, positionski, totalhours, note, bankaccount)
    VALUES (1111112, 'Male', 'unknown', 6, TRUE, 'unknown', 124, 'in WW1...', '77994466/7098');

INSERT INTO student(id, ski, snowboard, age, groupsize, note)
    VALUES (1111113, FALSE, TRUE, 10, 0, 'el barto');
INSERT INTO student(id, ski, snowboard, age, groupsize, note)
    VALUES (1111114, TRUE, FALSE, 8, 0, 'loves ponies');

INSERT INTO account(id, "login", "password", groupname, person_id)
    VALUES (1111110, 'homer', '6e017b5464f820a6c1bb5e9f6d711a667a80d8ea', 'instructor', 1111110);
INSERT INTO account(id, "login", "password", groupname, person_id)
    VALUES (1111111, 'marge', '6e017b5464f820a6c1bb5e9f6d711a667a80d8ea', 'instructor', 1111111);
INSERT INTO account(id, "login", "password", groupname, person_id)
    VALUES (1111112, 'abe', '6e017b5464f820a6c1bb5e9f6d711a667a80d8ea', 'administrator', 1111112);

INSERT INTO availability(id, starttime, endtime, note, instructor_id)
    VALUES (1111110, '2010-11-10 14:30', '2010-11-10 16:00', '', 1111110);
INSERT INTO availability(id, starttime, endtime, note, instructor_id)
    VALUES (1111111, '2010-11-10 17:45', '2010-11-10 19:00', '', 1111110);
INSERT INTO availability(id, starttime, endtime, note, instructor_id)
    VALUES (1111112, '2010-11-10 12:30', '2010-11-10 13:15', '', 1111110);
INSERT INTO availability(id, starttime, endtime, note, instructor_id)
    VALUES (1111113, '2010-11-10 12:30', '2010-11-10 15:00', '', 1111111);
INSERT INTO availability(id, starttime, endtime, note, instructor_id)
    VALUES (1111114, '2010-11-10 18:30', '2010-11-10 19:45', '', 1111111);