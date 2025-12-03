# FeedMobi

> Sistema de Gestão da Qualidade do Transporte Público Multimodal do DF

## Objetivo
Projeto de sistema para gerenciar as reclamações da população, monitorar o estado da frota de ônibus, controlar a infraestrutura urbana (paradas, estações, ciclovias) e avaliar comparativamente a satisfação dos usuários entre diferentes modais de transporte, com base nos problemas reais identificados no sistema de transporte do Distrito Federal.

![img_2.png](img_2.png)

## Modelo Relacional
![relacional.svg](relacional.svg)

## Stack
* MariaDB - Banco de Dados
* JDBC - Backend
* JavaFX - Frontend

## Diagrama de acesso à Camada de Persistência
![relacional.svg](dcp2.png)

## Como rodar aplicação localmente

## Perfis de Acesso
* Consulta de avaliações realizadas
* Incluir Nova Avaliação

### Usuário avaliador

### Gestor
![img_3.png](img_3.png)
* Dashboard
* Gestão de itens registro de Infraestrutura, Veículos, Funcionários, Percursos e Viagens

## Regras de Negócio

* Se deletar um usuário, são excluídas todas as avaliações feitas pelo usuário (ON CASCADE).
* Não é permitido excluir um motorista, veículo e/ou infraestrutura (parada, ciclovia) caso estejam vinculados a avaliações (manutenção do histórico).
Caso não estejam vinculados a nenhuma avaliação, permite a exclusão.


## Operações com banco de dados

### View

> Cria um relatório geral das avaliações usando UNION ALL para juntar o select das três tabelas relacionadas

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


### Trigger

### 