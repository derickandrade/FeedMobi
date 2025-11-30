

## Frontend

### Cadastrar usuário

### Página Inicial
- [ ] Tela Usuário avaliador
- [ ] Tela Usuário gestor

### Avaliações
- [ ] Tela Nova avaliação

### Gestão
- [ ] Tela gestão de

## Backend

### Funcionalidades de consulta
- [x] Retornar se um CPF já está cadastrado.
- [x] Retornar CPF, senha de um usuário e se ele é administrador.
- [ ] Retornar o código de uma viagem a partir do código do veículo, origem, destino, data e hora.
- [ ] Retornar o código de uma parada a partir do local.
- [ ] Retornar o código de uma ciclovia a partir do local.
- [ ] Retornar as avaliações de um CPF.
- [ ] Retornar todas avaliações
- [ ] Retornar todas paradas com respectivos status
- [ ] Retornar todas ciclovias com respectivos status
- [ ] Retornar todos veículos
- [ ] Retornar todos funcionários
- [ ] Retornar todos os percursos
- [ ] Retornar todas as viagens
- [ ] Retornar a placa de um veículo se houver

### Funcionalidades inserção
- [ ] Adicionar um usuário ao BD.
- [ ] Inserir uma avaliação (CPF, texto e nota).
- [ ] Inserir uma avaliação de viagem com o código da avaliação + código da viagem.
- [ ] Inserir uma avaliação de parada com o código da avaliação + código da parada.
- [ ] Inserir uma avaliação de ciclovia com o código da avaliação + código da ciclovia.
- [ ] Incluir uma parada (-> Parada)

### Funcionalidades de edição
- [ ] Atualizar campos de um usuário.
- [ ] Atualizar informações de uma parada (-> Parada)

### Funcionalidades de exclusão
- [ ] Excluir uma parada (-> Parada)

### Erros
- [ ] Retornar se houve erro ao adicionar uma avaliação
- 
### CRUD 
Retornar todas: avaliações, paradas e status, ciclovias e status, veículos, funcionários, percursos e viagens (uma função pra cada).
Retornar a placa de um veículo se houver.
Incluir, editar e excluir:
-o status de uma parada -> Parada_Status (Precisa fazer)
-uma ciclovia com ínicio e fim -> Ciclovia
-o status de uma ciclovia -> Ciclovia_Status
-um veículo com data limite de operação, assentos e capacidade em pé. -> Veículo
-uma placa de ônibus. -> Ônibus_Placa
-um funcionário com cpf, nome e sobrenome -> Motorista e Cobrador ? (Transformar em Funcionário)
-uma viagem com data, hora, percurso, motorista e veículo -> Viagem
-um cobrador associado a uma viagem -> Cobrador-Viagem
-um percurso (com origem e destino) -> Percurso
-um horário e dia da semana de um percurso -> Dias_Semana
-uma parada em um percurso -> Paradas
