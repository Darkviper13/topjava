DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);
INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2019-10-21 09:30', 'Завтрак', '500', 100000),
       ('2019-10-21 12:00', 'Обед', '1000', 100000),
       ('2019-10-21 18:30', 'Ужин', '510', 100000),
       ('2019-10-21 09:30', 'Завтрак', '500', 100001),
       ('2019-10-21 12:00', 'Обед', '1000', 100001),
       ('2019-10-21 18:30', 'Ужин', '500', 100001)

