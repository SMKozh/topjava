DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00', 'Завтрак', 500, 100000),
       ('2020-01-30 13:00', 'Обед', 1000, 100000),
       ('2020-01-30 20:00', 'Ужин', 500, 100000),
       ('2020-01-31 00:00', 'Еда на граничное значение', 100, 100000),
       ('2020-01-31 10:00', 'Завтрак', 1000, 100000),
       ('2020-01-31 13:00', 'Обед', 500, 100000),
       ('2020-01-31 20:00', 'Ужин', 410, 100000),
       ('2022-02-15 10:00', 'Завтрак для admin', 500, 100001),
       ('2022-02-15 13:00', 'Обед для admin', 1000, 100001),
       ('2022-02-15 20:00', 'Ужин для admin', 500, 100001),
       ('2022-02-16 00:00', 'Еда на граничное значение для admin', 100, 100001),
       ('2022-02-16 10:00', 'Завтрак для admin', 1000, 100001),
       ('2022-02-16 13:00', 'Обед для admin', 500, 100001),
       ('2022-02-16 20:00', 'Ужин для admin', 410, 100001);
