
-- para limpar tabelas que trocaram de nome
DROP TABLE IF EXISTS Viagem_Reclamacao;
DROP TABLE IF EXISTS Parada_Reclamacao;
DROP TABLE IF EXISTS Reclamacao;

-- para limpar tabelas
DROP TABLE IF EXISTS Cobrador_Viagem;
DROP TABLE IF EXISTS Viagem_Reclamacao;
DROP TABLE IF EXISTS Parada_Reclamacao;
DROP TABLE IF EXISTS Ciclovia_Reclamacao;
DROP TABLE IF EXISTS Viagem;
DROP TABLE IF EXISTS Cobrador;
DROP TABLE IF EXISTS Motorista;
DROP TABLE IF EXISTS Onibus_Placa;
DROP TABLE IF EXISTS Veiculo;
DROP TABLE IF EXISTS Horario_dia_percurso;
DROP TABLE IF EXISTS Paradas;
DROP TABLE IF EXISTS Percurso;
DROP TABLE IF EXISTS Parada;
DROP TABLE IF EXISTS Ciclovia;
DROP TABLE IF EXISTS Avaliacao;
DROP TABLE IF EXISTS Usuario;

-- para limpar procedures e triggers
DROP PROCEDURE IF EXISTS Media_Notas;
DROP PROCEDURE IF EXISTS Historico_Motorista;

-- criando as tabelas
CREATE TABLE Parada(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
	localizacao VARCHAR(15) UNIQUE
);

CREATE TABLE Ciclovia(
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    localizacao VARCHAR(15) UNIQUE
);

CREATE TABLE Percurso(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
	origem INT NOT NULL,
	destino INT NOT NULL,
    UNIQUE (origem, destino),
	FOREIGN KEY (origem) REFERENCES Parada(codigo),
	FOREIGN KEY (destino) REFERENCES Parada(codigo)
);

CREATE TABLE Paradas(
	percurso INT,
	localizacao INT,
	PRIMARY KEY (percurso, localizacao),
	FOREIGN KEY (percurso) REFERENCES Percurso(codigo),
	FOREIGN KEY (localizacao) REFERENCES Parada(codigo)
);

CREATE TABLE Horario_dia_percurso(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    hora TIME NOT NULL,
    dia CHAR(3),
    percurso INT,
    UNIQUE (hora, dia, percurso),
    FOREIGN KEY (percurso) REFERENCES Percurso(codigo)
);

CREATE TABLE Motorista(
	cpf VARCHAR(11) PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL,
	UNIQUE (nome, sobrenome)
);

CREATE TABLE Veiculo(
	numero INT AUTO_INCREMENT PRIMARY KEY,
	data_validade DATE NOT NULL,
	assentos SMALLINT NOT NULL,
	capacidade_em_pe SMALLINT NOT NULL
);

CREATE TABLE Onibus_Placa(
	numero INT PRIMARY KEY,
	placa CHAR(7),
	FOREIGN KEY (numero) REFERENCES Veiculo(numero) ON DELETE CASCADE
);

CREATE TABLE Cobrador(
	cpf VARCHAR(11) NOT NULL PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL,
	UNIQUE (nome, sobrenome)
);

CREATE TABLE Viagem(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    horario_dia_percurso INT NOT NULL,
	motorista VARCHAR(11) NOT NULL,
    cobrador VARCHAR(11),
	veiculo INT NOT NULL,
	FOREIGN KEY (horario_dia_percurso) REFERENCES Horario_dia_percurso(codigo),
	FOREIGN KEY (motorista) REFERENCES Motorista(cpf),
    FOREIGN KEY (cobrador) REFERENCES Cobrador(cpf),
	FOREIGN KEY (veiculo) REFERENCES Veiculo(numero)
);

CREATE TABLE Usuario(
	cpf CHAR(11) PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL,
	email VARCHAR(254) UNIQUE NOT NULL,
	senha VARCHAR(25) NOT NULL,
	administrador BOOL NOT NULL,
	UNIQUE (nome, sobrenome)
);

CREATE TABLE Avaliacao(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
	texto VARCHAR(500) NOT NULL,
	usuario CHAR(11) NOT NULL,
	nota INT NOT NULL,
	FOREIGN KEY (usuario) REFERENCES Usuario(cpf) ON DELETE CASCADE
);

CREATE TABLE Parada_Reclamacao(
	reclamacao INT PRIMARY KEY,
	parada INT,
    foto MEDIUMBLOB,
	FOREIGN KEY (reclamacao) REFERENCES Avaliacao(codigo) ON DELETE CASCADE,
	FOREIGN KEY (parada) REFERENCES Parada(codigo)
);

CREATE TABLE Viagem_Reclamacao(
	reclamacao INT PRIMARY KEY,
	viagem INT,
	FOREIGN KEY (reclamacao) REFERENCES Avaliacao(codigo) ON DELETE CASCADE,
	FOREIGN KEY (viagem) REFERENCES Viagem(codigo)
);

CREATE TABLE Ciclovia_Reclamacao(
    reclamacao INT PRIMARY KEY,
    ciclovia INT,
    foto MEDIUMBLOB,
    FOREIGN KEY (reclamacao) REFERENCES Avaliacao(codigo) ON DELETE CASCADE,
    FOREIGN KEY (ciclovia) REFERENCES Ciclovia(codigo)
);

-- view que cria relatório geral das avaliações usando union all para juntar o select das três tabelas relacionadas
CREATE OR REPLACE VIEW Relatorio_Geral_Avaliacoes AS
       SELECT
           A.codigo,
           A.texto,
           A.nota,
           A.usuario,
           'Viagem' AS tipo_avaliacao,
           V.viagem AS codigo_id
       FROM Avaliacao A
       JOIN Viagem_Reclamacao V ON A.codigo = V.reclamacao

        UNION ALL

        SELECT
            A.codigo,
            A.texto,
            A.nota,
            A.usuario,
            'Parada' AS tipo_avaliacao,
            P.parada AS codigo_id
       FROM Avaliacao A
       JOIN Parada_Reclamacao p on A.codigo = P.reclamacao

       UNION ALL

        SELECT
           A.codigo,
           A.texto,
           A.nota,
           A.usuario,
           'Ciclovia' AS tipo_avaliacao,
           C.ciclovia AS codigo_id
        FROM Avaliacao A
        JOIN Ciclovia_Reclamacao C on A.codigo = C.reclamacao;


-- procedure 1: para calcula media das avaliacoes
CREATE PROCEDURE Media_Notas()
BEGIN
SELECT
    COUNT(*) as Total_Avaliacoes,
    IFNULL(AVG(nota), 0) as Media_Geral
FROM Avaliacao;
END;

-- procedure 2: histórico de avaliações de um motorista
CREATE PROCEDURE Historico_Motorista(IN p_cpf VARCHAR(11))
BEGIN
SELECT
    M.cpf AS Motorista,
    OP.placa AS Onibus,
    IFNULL(A.nota, '-') AS Nota_Recebida
FROM Motorista M
     INNER JOIN Viagem V ON M.cpf = V.motorista
     INNER JOIN Horario_dia_percurso H ON V.horario_dia_percurso = H.codigo
     INNER JOIN Veiculo Vc ON V.veiculo = Vc.numero
     LEFT JOIN Onibus_Placa OP ON Vc.numero = OP.numero
     LEFT JOIN Viagem_Reclamacao VR ON V.codigo = VR.viagem
     LEFT JOIN Avaliacao A ON VR.reclamacao = A.codigo
WHERE M.cpf = p_cpf
ORDER BY H.dia, H.hora;
END;

-- trigger para tabela de log de gestão
-- cria tabela
CREATE TABLE Log_Gestao(
    codigo INT AUTO_INCREMENT,
    item_adicionado VARCHAR(15),
    item_conteudo VARCHAR(25),
    data_registro DATETIME DEFAULT CURRENT_TIMESTAMP
)

-- triggers quando adiciona parada, ciclovia, veiculo, motorista e cobrador
DROP TRIGGER IF EXISTS Ciclovia_Adicionada;
DROP TRIGGER IF EXISTS Veiculo_Adicionado;
DROP TRIGGER IF EXISTS Motorista_Adicionado;
DROP TRIGGER IF EXISTS Cobrador_Adicionado;

CREATE TRIGGER Ciclovia_Adicionada
    AFTER INSERT ON Ciclovia
    FOR EACH ROW
BEGIN

    INSERT INTO Log_Gestao (item_adicionado, item_conteudo, data_registro)
    VALUES ('Ciclovia', NEW.localizacao, CURRENT_TIMESTAMP());
END;


CREATE TRIGGER Veiculo_Adicionado
    AFTER INSERT ON Veiculo
    FOR EACH ROW
BEGIN

    INSERT INTO Log_Gestao (item_adicionado, item_conteudo, data_registro)
    VALUES ('Veiculo', 'Metrô/Ônibus', CURRENT_TIMESTAMP());
END;


CREATE TRIGGER Motorista_Adicionado
    AFTER INSERT ON Motorista
    FOR EACH ROW
BEGIN

    INSERT INTO Log_Gestao (item_adicionado, item_conteudo, data_registro)
    VALUES ('Motorista', NEW.cpf, CURRENT_TIMESTAMP());
END;


CREATE TRIGGER Cobrador_Adicionado
    AFTER INSERT ON Cobrador
    FOR EACH ROW
BEGIN

    INSERT INTO Log_Gestao (item_adicionado, item_conteudo, data_registro)
    VALUES ('Cobrador', NEW.cpf, CURRENT_TIMESTAMP());
END;


