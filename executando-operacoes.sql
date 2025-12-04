-- apresenta view
SELECT * FROM Relatorio_Geral_Avaliacoes;

-- chama procedure para ver a media
CALL Media_Nota();

-- chama procedure para ver historico de um motorista
CALL Historico_Motorista('42859249605');

-- testando o trigger de parada
INSERT INTO Parada (localizacao)
VALUES ('SQN 213 - L1');

-- testando o trigger de ciclovia
INSERT INTO Ciclovia (localizacao)
VALUES ('L2 Sul');

-- testando o trigger de Veiculo
INSERT INTO Veiculo (data_validade, assentos, capacidade_em_pe)
VALUES ('2030-12-01', 21, 18);

-- testando o trigger de Motorista
INSERT INTO Motorista (cpf, nome, sobrenome)
VALUES ('87650623882', 'Jorge', 'Beltrano');

-- testando o trigger de Cobrador
INSERT INTO Cobrador (cpf, nome, sobrenome)
VALUES ('44643550805', 'Fulano', 'José');

-- consultando tabela de logs
SELECT  * FROM Log_Gestao;