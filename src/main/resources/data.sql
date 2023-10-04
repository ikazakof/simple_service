INSERT INTO users_scheme.cities
    (
     title
    ) VALUES
    (
     'Москва'
    ),
    (
     'Волгоград'
    );

INSERT INTO users_scheme.users
    (
        first_name,
        last_name,
        middle_name,
        email,
        password,
        phone,
        city,
        registration_date,
        update_date

    ) VALUES
    (
    'Vladimir',
    'Matveev',
    'Anatolievich',
    'first@mail.ru',
    'somePass',
    '+79144235246',
    'Москва',
    '2023-09-18 12:34:56 Europe/Moscow',
    '2023-09-18 12:34:56 Europe/Moscow'
    ),
    (
        'Dmitriy',
        'Barmaleev',
        'Sergeevich',
        'second@mail.ru',
        'somePass2',
        '+79144235156',
        'Волгоград',
        '2023-09-17 12:34:56 Europe/Moscow',
        '2023-09-17 12:34:56 Europe/Moscow'
    ),
    (
        'Alexey',
        'Strugatskiy',
        'Vladimirovich',
        'third@mail.ru',
        'somePass3',
        '+79294235156',
        '',
        '2023-09-16 12:34:56 Europe/Moscow',
        '2023-09-16 12:34:56 Europe/Moscow'
    );