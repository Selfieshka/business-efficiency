INSERT INTO owner (first_name,
                   last_name,
                   patronymic,
                   age,
                   email,
                   phone_number,
                   password,
                   business_name,
                   role)
VALUES ('Кирилл',
        'Ахметов',
        'Эрикович',
        20,
        'kirill@gmail.com',
        '+77777777777',
        'hashed_password_12345',
        'Tomato Juice',
        'USER');

INSERT INTO position (name)
VALUES ('Стажёр'),
       ('Рабочий'),
       ('Директор'),
       ('Менеджер')