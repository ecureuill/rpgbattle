🚧 Version under construction

<div align="center">
  
# ⚔️ RPG Battle API ⚔️

![Docker](https://img.shields.io/badge/-Docker-000?&logo=Docker)
![Linux](https://img.shields.io/badge/-Linux-000?&logo=Linux)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)
![Postgresql](https://img.shields.io/badge/-Postgresql-000?&logo=Postgresql)
![Git](https://img.shields.io/badge/Git-000?&logo=Git)
![VSCode](https://img.shields.io/badge/VSCode-000?&logo=visualstudiocode)

</div>

## Sobre o Projeto

Este projeto é uma API REST desenvolvida como parte de um desafio back-end da empresa Avanade. A API é um sistema de batalhas RPG por turnos, onde os jogadores podem criar batalhas, selecionar personagens, determinar a iniciativa e realizar os turnos.

![image](https://github.com/ecureuill/rpgbattle-api/assets/993369/a954479d-ec99-4bdf-a6ea-4b90152e964f)
## Sobre a mecânica do jogo

- Criação de Batalha: os jogadores devem criar uma batalha, informando os dois oponentes.
- Seleção de personagens: Os jogadores devem escolher seus personagens antes da iniciativa ser determinada.
- Iniciativa: A batalha começa com um teste de dados para determinar o jogador que tem a iniciativa.
- Turno de ataque: O jogador rola um dado e o valor é somado aos atributos de força e agilidade do personagem para determinar o poder de ataque.
- Turno de defesa: O jogador oponente rola um dado e o valor é somado aos atributos de defesa e agilidade do personagem para determinar a capacidade de defesa.
- Ataque x Defesa: O sistema verifica se o poder de ataque é maior que a capacidade de defesa. Se for, um Turno de dano é habilitado, caso contrário o turno se encerra e um novo é iniciado.
- Turno de dano: O jogador atacante rola os dados e o valor é somado ao atributo de força do personagem. O dano, então, é subtraído dos pontos de vida do personagem do oponente.
- Fim da batalha: A batalha termina quando um dos jogadores perde todos os pontos de vida do seu personagem.

## Tecnologias Utilizadas
Este projeto utiliza as seguintes tecnologias:
- Linguagem: Java 17
- Gerenciador de Depências: Maven 3.3.1
- Framework: Spring Boot
- Documentação: SpringDoc
- Database: Postgresql
- Ambiente de Desenvolvimento: Docker
- Editor: VS Code

O uso do Spring Boot facilita a criação de APIs REST, enquanto o SpringDoc fornece uma documentação automatizada da API. O PostgreSQL foi escolhido como banco de dados devido ao requisito do desafio. O Docker é utilizado para criar um ambiente de desenvolvimento isolado e replicável. O VS Code foi utilizado por sua facilidade em desenvolver em um container docker.

## Funcionalidades
A API oferece funcionalidades que  permitem aos jogadores interagir com o sistema criando personagens, participando de batalhas e acompanhando o progresso do jogo.

- **Criação de Usuário**: permite criar um novo usuário no sistema.
- **CRUD de Personagens**: permite criar, editar, consultar e excluir personages do jogo.
- **Criação de Batalha**: permite criar uma nova batalha entre dois jogadores
- **Seleção de personagens**: permite e selecionar personagens que participarão de uma batalha
- **Batalhar**
  - **Iniciativa**: permite determinar a ordem do turno dos jogadores
  - **Disputar o turno**: permite realizar ataques, defesas e danos em uma batalha
- **Consulta de batalhas**: permite aos usuários obter informações sobre todas as batalhas registradas no sistema ou sobre uma batalha específica.
- **Consulta de Log**: permite obter o registro de eventos e ações ocorridos durante uma batalha.

## Instalação e execução
Para utilizar o projeto, siga as instruções abaixo:

1. **Pré-requisitos**: Certifique-se de ter o Java JDK 17 e o Maven instalados em sua máquina.

1. **Clone do repositório**: Faça um clone deste repositório em sua máquina local.

1. **Instalação das dependências**: Navegue até o diretório raiz do projeto e execute o comando `mvn install` para instalar as dependências necessárias.

1. **Configuração do banco de dados**: O projeto utiliza um banco de dados PostgreSQL. Certifique-se de ter o PostgreSQL instalado em sua máquina e crie um banco de dados vazio para ser utilizado pelo projeto.

1. **Configuração das variáveis de ambiente**: Crie um arquivo `.env` na raiz do projeto e defina as seguintes variáveis de ambiente:

    ```
    DATABASE_URL=jdbc:postgresql://localhost:5432/nome_do_banco
    DATABASE_USERNAME=seu_usuario
    DATABASE_PASSWORD=sua_senha
    ```

    Substitua `nome_do_banco`, `seu_usuario` e `sua_senha` pelos valores correspondentes ao seu ambiente.

1. **Execução do projeto**: Execute o comando `mvn spring-boot:run` para iniciar a aplicação.

1. **Acesso à API**: A API estará disponível em http://localhost:8080. Você pode utilizar um cliente de API, como o Postman, para testar os endpoints, ou a interface Swagger-UI em http://localhost:8080/swagger-ui/index.html.

## Endpoints
A API possui os seguintes endpoints:

- **POST** `/characters`: Cria um novo personagem
- **GET** `/characters`: Retorna informações sobre todos os personagens
- **GET** `/characters/{specie}`: Retorna informações sobre um personagem específico.
- **PUT** `/characters/{specie}`: Atualiza informações sobre um personagem específico.
- **DELETE** `/characters/{specie}`: Exclui um personagem específico.
- **POST** `/battles`: Cria uma nova batalha.
- **POST** `/battles/{battleId}/{player}`: Seleciona um personagem para a batalha.
- **POST** `/battles/{battleId}/Initiative`: Determina a iniciativa da batalha.
- **POST** `/battles/{battleId}/turns`: Avança os turnos da batalha.
- **GET** `/battles`: Retorna informações sobre todas as batalhas.
- **GET** `/battles/{battelId}`: Retorna informações sobre uma batalha específica.
- **GET** `/logs/{battleId}`: Retorna informações completa sobre uma batalha específica, incluindo todos turnos realizados.
- **GET** `/users/`: Retorna a lista de usuários cadastrados
- **POST** `users`: Crie um usuário

### Exemplos de comandos 
A seguir, estão exemplos de comandos para utilizar as funcionalidades principais da API.
 
A ***Documentação completa*** da API, incluindo detalhes sobre todos os endpoints, pode ser acessada em [swager-ui](http://localhost:8080).

- **Criar um usuário**
  ```json
  POST /users
  Body:
  {
    "username": "Nome do Usuário",
    "email": "usuario@example.com",
    "password": "12345678"
  }
  ```
- **Criar um personagem**
  ```json
  POST /characters
  Body:
  {
    "type": "HERO ou MONSTER",
    "specie": "nome do personagem",
    "life": 20,
    "strength": 7,
    "defence": 4,
    "agility": 4,
    "dice": "2d6"
  }
  ```
- **Criar uma nova batalha**
  ```json
  POST /battles
  Body:
  {
    "playerOne": "username do jogador 1",
    "playerTwo": "username do jogador 2"
  }
  ```
- **Selecionar personagens para a batalha**

  ```json
  POST /battles/{battleId}/{playerId}
  Body:
  {
    "specie": "nome do personagem"
  }
  ```
- **Avançar os turnos da batalha**
  ```
  POST /battles/{battleId}/turns
  ```

## O desenvolvimento
A abordagem DDD foi adotada para garantir que o sistema seja modelado de acordo com as regras e conceitos do domínio do jogo.
Foi adotada uma linguagem ubíqua para nomear as classes e métodos de acordo com os termos do domínio do jogo, como `Character`, `Battle`, `Player`, `Dice`, entre outros.

### Estrutura do projeto
- **domain**: Este pacote contém as classes que representam o domínio do jogo. Aqui estão as entidades principais, como Character e Battle, bem como as regras de negócio relacionadas à batalha.
- **application**: Este pacote contém as classes que implementam a lógica de aplicação, como a inicialização da batalha, a execução dos turnos e o cálculo do resultado.
- **infrastructure**: Este pacote contém as classes responsáveis pela persistência de dados ou por qualquer integração externa necessária.

### Padrões de Projetos Utilizados
Foram aplicados os seguintes padrões de projetos:

#### State

O padrão de projeto State foi utilizado para gerenciar o comportamento do objeto `Battle` de acordo com seu estado interno. O objeto `Battle` pode ter diferentes estados, cada um representado por uma implementação da interface `BattleState`. Através do método `setNextState`, o estado do objeto `Battle` é alterado para o próximo estado, modificando assim seu comportamento. Isso permite uma melhor organização e flexibilidade no gerenciamento das transições de estado e comportamento do objeto `Battle`.

#### Chain of Responsability
A combinação dos padrões de projeto Chain of Responsability e State foi realizado para que cada estado saiba qual é o próximo e poder passar a responsabilidade para ele.

#### Strategy
Como resultado da implementação do padrão de projeto State, cada estado precisaria sobrescrever métodos que não seriam utiliados, violando o SOLID.
O padrão Strategy foi utilizado em combinação com o padrão State para encapsular cada método em uma interface específica, implementada apenas pelo estado que realmente precisam.

#### Singleton
O padrão de projeto Singleton foi utilizado para que a mesma instãncia da classe `Random` seja utilizada pelo método `roll` da classe `Dice` que é compartilhado durante a iniciativa e os turnos. Isso garante que os lancamentos dos dados terão uma sequência consistente de números aleatórios.

## Contribuindo
Se você deseja contribuir para o desenvolvimento deste projeto, siga as etapas abaixo:

1. Faça um fork deste repositório.
1. Crie uma branch com sua nova feature: `git checkout -b minha-feature`
1. Commit suas alterações: `git commit -m 'Adicionando nova feature'`
1. Faça push para a branch: `git push origin minha-feature`
1. Abra uma Pull Request.

## Licença
Este projeto está licenciado sob a Licença MIT.

## Contato
Em caso de dúvidas ou sugestões, entre em contato:

 [![Gmail Badge](https://img.shields.io/badge/-Gmail-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:logika.sciuro@gmail.com)](mailto:logika.sciuro@gmail.com) [![Linkedln](https://img.shields.io/badge/LinkedIn-0077B5?style=flat-square&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/camillasilva)
