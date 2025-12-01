

## Frontend

### Login
- [x] Tela de logar

### Usuário
- [ ] Tela cadastrar usuário
- [ ] Tela editar cadastro usuário

### Página Inicial
- **Tela Usuário avaliador**
  - [x] Sem dados
  - [ ] Com dados
- **Tela Usuário gestor** 
  - [x] Sem dados
  - [ ] Com dados

### Avaliações
- [ ] Tela Nova avaliação

### Gestão
- **Tela gestão de Avaliações**
  - [ ] Consulta
    - [ ] Com dados
    - [ ] Sem dados
  - [ ] Incluir Avaliação
  - [ ] Editar Avaliação
- **Tela gestão de Infraestrutura**
    - [ ] Consulta
        - [ ] Com dados
        - [ ] Sem dados
    - [ ] Incluir Infraestrutura
    - [ ] Editar Infraestrutura
- **Tela gestão de Veículos**
    - [ ] Consulta
        - [ ] Com dados
        - [ ] Sem dados
    - [ ] Incluir Veículo
    - [ ] Editar Veículo
- **Tela gestão de Funcionários**
    - [ ] Consulta
        - [ ] Com dados
        - [x] Sem dados
    - [ ] Incluir Funcionário
    - [ ] Editar Funcionário
- **Tela gestão de Percursos**
    - [ ] Consulta
        - [ ] Com dados
        - [ ] Sem dados
    - [ ] Incluir Percurso
    - [ ] Editar Percurso
- **Tela gestão de Viagens**
    - [ ] Consulta
        - [ ] Com dados
        - [ ] Sem dados
    - [ ] Incluir Viagem
    - [ ] Editar Viagem

## Backend

### Funcionalidades de consulta
- [x] Retornar se um CPF já está cadastrado.
- [x] Retornar CPF, senha de um usuário e se ele é administrador.
- [x] Retornar o código de uma viagem a partir do código do veículo, origem, destino, data e hora.
- [x] Retornar o código de uma parada a partir do local.
- [x] Retornar o código de uma ciclovia a partir do local.
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
- [x] Adicionar um usuário ao BD.
- [ ] Inserir uma avaliação (CPF, texto e nota).
- [ ] Inserir uma avaliação de viagem com o código da avaliação + código da viagem.
- [ ] Inserir uma avaliação de parada com o código da avaliação + código da parada.
- [ ] Inserir uma avaliação de ciclovia com o código da avaliação + código da ciclovia.
- [ ] Incluir uma parada -> Parada
- [ ] Incluir uma ciclovia  -> Ciclovia
- [ ] Incluir um veículo -> Veiculo
- [ ] Incluir um funcionário -> Funcionário
- [ ] Incluir um percurso -> Percurso
- [ ] Incluir uma viagem -> Viagem

### Funcionalidades de edição
- [x] Atualizar campos de um usuário.
- [ ] Atualizar uma parada 
- [ ] Atualizar uma ciclovia 
- [ ] Atualizar um veículo
- [ ] Atualizar um funcionário
- [ ] Atualizar um Percurso
- [ ] Atualizar uma viagem -> Viagem

### Funcionalidades de exclusão
- [ ] Excluir uma parada / apenas se não vinculado a avaliação
- [ ] Excluir uma ciclovia / apenas se não vinculado a avaliação
- [ ] Excluir um veículo / apenas se não vinculado a avaliação
- [ ] Excluir um funcionário / apenas se não vinculado a avaliação
- [ ] Excluir um percurso / apenas se não vinculado a avaliação
- [ ] Excluir uma viagem / apenas se não vinculado a avaliação

### Erros
- [ ] Retornar se houve erro ao adicionar uma avaliação
