-- dump para popular as tabelas

-- começa por tabelas independentes
INSERT INTO Usuario (cpf, nome, sobrenome, email, senha, administrador)
VALUES ('70679007946', 'Giovana', 'Lucas Marques', 'giovana@df.gov.br', '1234', '1'),
       ('74617709185', 'Luisa', 'Peixoto', 'lulis@yahoo.com', '1234', '0'),
       ('73741897884', 'Paulo', 'Neto', 'paulo@gmail.com', '1234', '0'),
       ('67604358934', 'Lucas', 'Goulart', 'lukeimyourfather@gmail.com', '1234', '0'),
       ('59753796609', 'Inacio', 'da Silva', 'inacio.silva@bol.com.br', '1234', '0');

INSERT INTO Motorista (cpf, nome, sobrenome)
VALUES ('42859249605', 'Beltrano', 'Reis'),
       ('45164699443', 'José', 'Almeida'),
       ('85524816772', 'Maria', 'Medeiros'),
       ('62076640606', 'Larissa', 'Diniz Souza'),
       ('02814672185', 'Bruna', 'Souza Silva');

INSERT INTO Cobrador (cpf, nome, sobrenome)
VALUES ('83407015844', 'Benício', 'Sobrinho'),
       ('17748796548', 'José', 'Ferreira'),
       ('38186417141', 'Leide', 'Gonçalves'),
       ('21344962904', 'João', 'Medeiros Morais');

INSERT INTO Parada (codigo, localizacao)
VALUES (1, 'SQN 215 - L1'),
       (2, 'SQN 106 - EixoW'),
       (3, 'CLSW 504 - 3Av'),
       (4, 'QNL AE 2'),
       (5, 'Rod. P. Piloto'),
       (6, 'Term. Asa Sul'),
       (7, 'SGAS 615 - L2'),
       (8, 'Torre de TV'),
       (9, 'Pça Relógio');

INSERT INTO Ciclovia (codigo, localizacao)
VALUES (1, 'L2 NORTE'),
       (2,'Pq Cidade E10'),
       (3,'Eixão Norte');

INSERT INTO Veiculo (numero, data_validade, assentos, capacidade_em_pe)
VALUES (1,'2030-12-01', 42, 20), -- numero 1 (auto increment)
       (2,'2029-06-15', 38, 15), -- numero 2
       (3,'2028-01-20', 60, 40), -- numero 3
       (4,'2031-11-30', 42, 20), -- numero 4
       (5,'2030-05-10', 25, 10); -- numero 5

-- tabelas dependentes de nível 1
INSERT INTO Onibus_Placa (numero, placa)
VALUES (1, 'GTD3F23'),
       (3, 'VKR7R29'),
       (4, 'PSE7A20'),
       (5, 'SUW7H87');

INSERT INTO Percurso (codigo, origem, destino)
VALUES (1, 6,5),  -- codigo 1 (autoincrement) de 6 - Terminal Asa Sul até 5 Rodoviária
       (2, 5,1),
       (3, 4,5),
       (4, 9,5),
       (5, 5,7);

-- tabelas dependentes de nível 3
INSERT INTO Paradas (percurso, localizacao)
VALUES (1,7),
       (4,8);

INSERT INTO Horario_dia_percurso (codigo, hora, dia, percurso)
VALUES (1,'13:20', 'seg', 1),
       (2,'08:00', 'SEG', 2),
       (3,'18:00', 'TER', 3),
       (4,'07:30', 'QUA', 4),
       (5,'12:00', 'SEX', 1);

INSERT INTO Viagem (codigo, horario_dia_percurso, motorista, cobrador, veiculo)
VALUES (1, 1,'42859249605', '83407015844', 1),
       (2, 2,'45164699443', '17748796548', 2),
       (3, 3,'85524816772', '38186417141', 3),
       (4, 4,'42859249605', '21344962904', 1);

INSERT INTO Avaliacao (codigo, texto, usuario, nota)
VALUES (1,'Foi ótima, cobrador muito simpático e o ônibus bem limpinho com ar condicionado!', '74617709185', 5 ),
       (2,'Um horror, demorou demais!', '67604358934', 2),
       (3,'O chão todo desnivelado, alguém pode cair e se machucar!!', '59753796609', 3),
       (4,'A parada tá caindo aos pedaços, alo GDF faz alguma coisa', '67604358934', 1),
       (5,'Motorista correu muito, fiquei apavorada', '73741897884', 1), -- ID 5
       (6,'Amei a ciclovia, muito arborizada', '70679007946', 5);    -- ID 6;

INSERT INTO Parada_Reclamacao (reclamacao, parada, foto)
VALUES (4,2,NULL);

INSERT INTO Viagem_Reclamacao (reclamacao, viagem)
VALUES (1,1),
       (2,2),
       (5,4);

INSERT INTO Ciclovia_Reclamacao (reclamacao, ciclovia, foto)
VALUES (3,1, NULL),
       (6,2, NULL);

