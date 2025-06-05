INSERT INTO owner (first_name,
                   last_name,
                   patronymic,
                   age,
                   email,
                   phone_number,
                   password,
                   business_name)
VALUES ('Кирилл',
        'Ахметов',
        'Эрикович',
        20,
        'kirill@gmail.com',
        '+799872797711',
        'hashed_password_12345',
        'Tomato Juice');

INSERT INTO position (name)
VALUES ('Стажёр'),
       ('Рабочий'),
       ('Директор'),
       ('Менеджер')