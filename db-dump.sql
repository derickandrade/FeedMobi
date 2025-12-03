-- dump para popular as tabelas
INSERT INTO Parada (codigo, localizacao)
VALUES ('1', 'Via L1 NORTE - SQN 215'),
       ('2', 'Eixo W Norte - SQN 106'),
       ('3', '3a Avenida - CLSW 504'),
       ('4', 'Via LN29 - QNL AE 2'),
       ('5', 'Rodoviária Plano Piloto'),
       ('6', 'Terminal Asa Sul'),
       ('7', 'Via L2 Sul - SGAS 615');

INSERT INTO Ciclovia (codigo, localizacao)
VALUES ('1', 'L2 NORTE');

INSERT INTO Percurso (codigo, origem, destino)
VALUES ('1', '6', '5'),
       ('2', '5', '1'),
       ('3', '4', '5');

INSERT INTO Paradas (percurso, localizacao)
VALUES ('1', '7');

INSERT INTO Horario_dia_percurso (codigo, hora, dia, percurso)
VALUES ('1', '13:20', 'seg', '1');

INSERT INTO Motorista (cpf, nome, sobrenome)
VALUES ('42859249605', 'Beltrano', 'Reis'),
       ('45164699443', 'José', 'Ferreira'),
       ('85524816772', 'Maria', 'Medeiros'),
       ('62076640606', 'Larissa', 'Diniz Souza'),
       ('02814672185', 'Bruna', 'Souza Silva');

INSERT INTO Cobrador (cpf, nome, sobrenome)
VALUES ('83407015844', 'Benício', 'Sobrinho'),
       ('17748796548', 'José', 'Ferreira'),
       ('38186417141', 'Leide', 'Gonçalves'),
       ('21344962904', 'João', 'Medeiros Morais'),
       ;

INSERT INTO Veiculo (numero, data_validade, assentos, capacidade_em_pe)
VALUES ('1', '2030-12-01', '42', '20');

INSERT INTO Onibus_Placa (numero, placa)
VALUES ('1', 'GTD3F23'),
       ('3', 'VKR7R29'),
       ('4', 'PSE7A20'),
       ('7', 'SUW7H87');

INSERT INTO Viagem (codigo, horario_dia_percurso, motorista, cobrador, veiculo)
VALUES ('1', '1', '1', '2');

INSERT INTO Usuario (cpf, nome, sobrenome, email, senha, administrador)
VALUES ('70679007946', 'Giovana', 'Lucas Marques', 'giovana@df.gov.br', '1234', '1'),
       ('74617709185', 'Luisa', 'Peixoto', 'lulis@yahoo.com', '1234', '0'),
       ('73741897884', 'Paulo', 'Neto', 'paulo@gmail.com', '1234', '0'),
       ('67604358934', 'Lucas', 'Goulart', 'lukeimyourfather@gmail.com', '1234', '0'),
       ('59753796609', 'Inacio', 'da Silva', 'inacio.silva@bol.com.br', '1234', '0');

INSERT INTO Avaliacao (codigo, texto, usuario, nota)
VALUES ('1', 'Foi ótima, cobrador muito simpático e o ônibus bem limpinho com ar condicionado!', '74617709185', '5' ),
       ('2', 'Um horror, demorou demais!', '67604358934', '2'),
       ('3', 'O chão todo desnivelado, alguém pode cair e se machucar!!', '59753796609', '3'),
       ('4', 'A parada tá caindo aos pedaços, alo GDF faz alguma coisa', '67604358934', '1');

INSERT INTO Parada_Reclamacao (reclamacao, parada, foto)
VALUES ('4', '2', '');

INSERT INTO Viagem_Reclamacao (reclamacao, viagem)
VALUES ('1', '1');

INSERT INTO Ciclovia_Reclamacao (reclamacao, ciclovia, foto)
VALUES ('3', '1', '');

