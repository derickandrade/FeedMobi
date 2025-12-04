# FeedMobi

> Sistema de Gestão da Qualidade do Transporte Público Multimodal do DF

## Objetivo
Projeto para gerenciar reclamações da população, monitorar frota, controlar infraestrutura urbana (paradas, estações, ciclovias) e avaliar satisfação dos usuários entre diferentes modais no Distrito Federal.

![img_2.png](img_2.png)

## Requisitos
- Java 17+ (ou versão usada no projeto)
- MariaDB (10.3+ recomendado)
- Build tool: Gradle ou Maven (conforme o projeto)
- IDE: IntelliJ IDEA / VS Code (com suporte a JavaFX)

## Stack
* MariaDB - Banco de Dados
* JDBC - Backend
* JavaFX - Frontend

## Perfis de Acesso
* Usuário avaliador
  * Consultar avaliações
  * Incluir nova avaliação
* Gestor
  * Dashboard
  * Gestão de Infraestrutura, Veículos, Funcionários, Percursos e Viagens

## Regras de Negócio

* Se deletar um usuário, são excluídas todas as avaliações feitas pelo usuário (ON CASCADE).
* Não é permitido excluir um motorista, veículo e/ou infraestrutura (parada, ciclovia) caso estejam vinculados a avaliações (manutenção do histórico).
Caso não estejam vinculados a nenhuma avaliação, permite a exclusão.


## Operações com banco de dados

### View

> Consolida um relatório geral das avaliações (Viagem, Parada, Ciclovia). 

```sql

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

```

```sql
SELECT * FROM Relatorio_Geral_Avaliacoes;
```


### Trigger
> Criação de uma tabela de Log e triggers para saber exatamente quando um item foi adicionado ao sistema de gestão

```sql
CREATE TABLE Log_Gestao(
   codigo INT AUTO_INCREMENT,
   item_adicionado VARCHAR(15),
   item_conteudo VARCHAR(25),
   data_registro DATETIME DEFAULT CURRENT_TIMESTAMP
)

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
```

```sql

INSERT INTO Parada (localizacao)
VALUES ('SQN 213 - L1');

INSERT INTO Ciclovia (localizacao)
VALUES ('L2 Sul');

INSERT INTO Veiculo (data_validade, assentos, capacidade_em_pe)
VALUES ('2030-12-01', 21, 18);

INSERT INTO Motorista (cpf, nome, sobrenome)
VALUES ('87650623882', 'Jorge', 'Beltrano');

INSERT INTO Cobrador (cpf, nome, sobrenome)
VALUES ('44643550805', 'Fulano', 'José');

SELECT  * FROM Log_Gestao;
```

### Procedure
> Procedure para calcular média das avaliações e para consultar histórico de avaliações de um motorista
```sql

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
```

```sql
CALL Media_Nota();
```

```sql
CALL Historico_Motorista('42859249605');
```