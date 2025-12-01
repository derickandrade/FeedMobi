
-- para limpar tabelas que trocaram de nome
DROP TABLE IF EXISTS Viagem_Reclamacao;
DROP TABLE IF EXISTS Parada_Reclamacao;
DROP TABLE IF EXISTS Reclamacao;

-- para limpar tabelas
DROP TABLE IF EXISTS Cobrador_Viagem;
DROP TABLE IF EXISTS Viagem_Reclamacao;
DROP TABLE IF EXISTS Parada_Reclamacao;
DROP TABLE IF EXISTS Ciclovia_Reclamacao;
DROP TABLE IF EXISTS Cobrador;
DROP TABLE IF EXISTS Viagem;
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

CREATE TABLE Viagem(
	codigo INT AUTO_INCREMENT PRIMARY KEY,
    horario_dia_percurso INT NOT NULL,
	motorista VARCHAR(11) NOT NULL,
	veiculo INT NOT NULL,
	FOREIGN KEY (horario_dia_percurso) REFERENCES Horario_dia_percurso(codigo),
	FOREIGN KEY (motorista) REFERENCES Motorista(cpf),
	FOREIGN KEY (veiculo) REFERENCES Veiculo(numero)
);

CREATE TABLE Cobrador(
	cpf VARCHAR(11) NOT NULL PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL,
	UNIQUE (nome, sobrenome)
);

CREATE TABLE Cobrador_Viagem(
	viagem INT NOT NULL,
	cobrador VARCHAR(11) NOT NULL,
	PRIMARY KEY (viagem, cobrador),
	FOREIGN KEY (cobrador) REFERENCES Cobrador(cpf),
	FOREIGN KEY (viagem) REFERENCES Viagem(codigo)
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