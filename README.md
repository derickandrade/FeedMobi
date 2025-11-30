# FeedMobi

> Sistema de Gestão da Qualidade do Transporte Público Multimodal do DF

Projeto de sistema para gerenciar as reclamações da população, monitorar o estado da frota de ônibus, controlar a infraestrutura urbana (paradas, estações, ciclovias) e avaliar comparativamente a satisfação dos usuários entre diferentes modais de transporte, com base nos problemas reais identificados no sistema de transporte do Distrito Federal.


## Regras de Negócio

### Cadastro
* Se deleta usuário -> Deleta todas as avaliacoes feitas pelo usuário (colocar um ON CASCADE ali no sql)

### Avaliação

### Gestão
* Não permite deletar motorista, veiculo e/ou parada caso estejam vinculados a avaliacoes (precisa manter a referencia do que esta sendo avaliado)
Caso não estejam vinculados, permite a deleção.
