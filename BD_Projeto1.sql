-- para limpar tabelas
DROP TABLE IF EXISTS Cobrador_Viagem;
DROP TABLE IF EXISTS Viagem_Reclamacao;
DROP TABLE IF EXISTS Parada_Reclamacao;
DROP TABLE IF EXISTS Cobrador;
DROP TABLE IF EXISTS Viagem;
DROP TABLE IF EXISTS Motorista;
DROP TABLE IF EXISTS Onibus_Placa;
DROP TABLE IF EXISTS Veiculo;
DROP TABLE IF EXISTS Dias_Semana;
DROP TABLE IF EXISTS Horario_Inicio;
DROP TABLE IF EXISTS Paradas;
DROP TABLE IF EXISTS Percurso;
DROP TABLE IF EXISTS Parada;
DROP TABLE IF EXISTS Reclamacao;
DROP TABLE IF EXISTS Usuario;


-- criando as tabelas
CREATE TABLE Parada(
	codigo SERIAL PRIMARY KEY,
	localizacao VARCHAR(15)
);

CREATE TABLE Percurso(
	codigo SERIAL PRIMARY KEY,
	origem INT NOT NULL,
	destino INT NOT NULL,
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

CREATE TABLE Horario_Inicio(
	hora TIME NOT NULL,
	percurso INT NOT NULL,
	PRIMARY KEY (hora, percurso),
	FOREIGN KEY (percurso) REFERENCES Percurso(codigo)
);

CREATE TABLE Dias_Semana(
	hora TIME NOT NULL,
	percurso INT,
	dia CHAR(3),
	PRIMARY KEY (hora, percurso, dia),
	FOREIGN KEY (hora, percurso) REFERENCES Horario_Inicio(hora, percurso)
);

CREATE TABLE Motorista(
	cpf VARCHAR(11) PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL
);

CREATE TABLE Veiculo(
	numero SERIAL PRIMARY KEY,
	data_validade DATE NOT NULL,
	assentos SMALLINT NOT NULL,
	capacidade_em_pe SMALLINT NOT NULL
);

CREATE TABLE Onibus_Placa(
	numero SERIAL PRIMARY KEY,
	placa CHAR(7),
	FOREIGN KEY (numero) REFERENCES Veiculo(numero)
);

CREATE TABLE Viagem(
	codigo SERIAL PRIMARY KEY,
	dt DATE NOT NULL,
	hora_inicio TIME NOT NULL,
	percurso INT NOT NULL,
	motorista VARCHAR(11) NOT NULL,
	veiculo INT NOT NULL,
	FOREIGN KEY (hora_inicio, percurso) REFERENCES Horario_Inicio(hora, percurso),
	FOREIGN KEY (motorista) REFERENCES Motorista(cpf),
	FOREIGN KEY (veiculo) REFERENCES Veiculo(numero)
	
);

CREATE TABLE Cobrador(
	cpf VARCHAR(11) NOT NULL PRIMARY KEY,
	nome VARCHAR(15) NOT NULL,
	sobrenome VARCHAR(15) NOT NULL
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
	senha VARCHAR(25)
);

CREATE TABLE Reclamacao(
	codigo SERIAL PRIMARY KEY,
	texto VARCHAR(500) NOT NULL,
	usuario CHAR(11) NOT NULL,
	FOREIGN KEY (usuario) REFERENCES Usuario(cpf)
);

CREATE TABLE Parada_Reclamacao(
	reclamacao INT PRIMARY KEY,
	parada INT,
	FOREIGN KEY (reclamacao) REFERENCES Reclamacao(codigo),
	FOREIGN KEY (parada) REFERENCES Parada(codigo)
);

CREATE TABLE Viagem_Reclamacao(
	reclamacao INT PRIMARY KEY,
	viagem INT,
	FOREIGN KEY (reclamacao) REFERENCES Reclamacao(codigo),
	FOREIGN KEY (viagem) REFERENCES Viagem(codigo)
	
);



