insert into config (`key`, value)
values ('AWS_ACCESS_KEY_ID', 'sua_chave_aqui');
insert into config (`key`, value)
values ('AWS_SECRET_ACCESS_KEY', 'sua_secret_aqui');

INSERT INTO `users` (`user_name`, `full_name`, `password`, `account_non_expired`, `account_non_locked`,
                     `credentials_non_expired`, `enabled`, `email`, `description`)
VALUES ('gustavo', 'Gustavo Becker', 'd9dd76e9cc0c5d99a763b93a657d983496858208210b79280dd09d62d4cd9fe9adea67e5af5f4e06',
        b'1', b'1', b'1', b'1', 'gustavofln@pronton.me', 'beatmaker de trap'),
       ('mariana', 'Mariana ramos', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3',
        b'1', b'1', b'1', b'1', 'gustavofln@pronton.me', 'beatmaker de R&B');

INSERT INTO `permission` (`description`)
VALUES ('ADMIN'),
       ('MANAGER'),
       ('COMMON_USER');


INSERT INTO `user_permission` (`id_user`, `id_permission`) VALUES(1, 1), (2, 1), (1, 2);