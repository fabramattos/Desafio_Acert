# Controle de Delivery de Restaurante
Este é um projeto simplificado para o controle de delivery de um restaurante, desenvolvido como parte do processo seletivo para a posição de Desenvolvedor Java Back-end.

## Stack Solicitada
### API RESTful:
- Java 17
- Spring Boot 2.7.X
- Banco de Dados PostgresSQL
- Maven
- OpenAPI 3.0 (Swagger)

## Funcionalidades
### Segurança:
- Permitir o cadastro de usuários e login com autenticação via token JWT. Os métodos das APIs abaixo só poderão ser executados caso o usuário esteja logado.
### Cliente:
- Permitir o cadastro, alteração, deleção e consulta de clientes.
### Pedido:
- Permitir o cadastro, alteração, deleção e consulta de pedidos. Um pedido obrigatoriamente precisa ter um cliente e um cliente pode ter vários pedidos.
### Entrega:
- Permitir o cadastro, alteração, deleção e consulta de entregas. Uma entrega obrigatoriamente necessita estar vinculada a um pedido.

### Obs sobre Deleção:
Ao deletar um usuário, verificações são feitas. Usuários e pedidos só podem ser deletados se não houver entrega em andamento.
Caso delete usuário, todos dados atrelados são deletados.
Caso delete pedido, entrega é deletada.
Caso delete entrega, pedido fica desassociado de uma entrega.

## Instruções para Execução
ℹ️Informações sobre autenticação jwt e login  estarão disponíveis ao acessar o link do Swagger-UI da aplicaçãoℹ️
### Via Web:
⚠️servidor será desativado após avaliação⚠️

https://acert.up.railway.app/swagger-ui/index.html
### Localmente, sem Docker:
1. Certifique-se de ter instalado em sua máquina: Java 17, PostgresSQL
2. No PostgreSQL, crie um banco de dados para a aplicação.
3. Clone este repositório em seu ambiente local.
4. Configure as credenciais do banco de dados no arquivo application.yml.
5. Execute o comando Maven ```mvn spring-boot:run```
6. A API estará disponível no seguinte endereço: http://localhost:8080/swagger-ui.html


### Localmente, com Docker:
😎dispensa configurações nos arquivos do projeto😎
1. Certifique-se de ter instalado e em execução na sua máquina: Docker, Java 17
2. Clone este repositório em seu ambiente local.
3. Execute os seguintes comandos no terminal da IDE (na raiz do projeto):
  - Maven: ```mvn package``` (Gera o artefato)
  - Docker: ```docker build -t acert-api -f Dockerfile-dev .``` (utiliza o arquivo DockerFile-dev para gerar a imagem docker)
  - Docker: ```docker-compose up``` (utiliza o arquivo Docker-Compose para configurar e executar a API e o PostgresSQL)
4. A API estará disponível no seguinte endereço: http://localhost:8080/swagger-ui.html


## Melhorias futuras:
Projeto focado no MVP devido trabalho para avaliação e tempo disponível.
Algumas ideias anotadas para implementação futura:
### No app
- Testes para os Controllers com mockMvc.
- criação de arquitetura para "Itens", substituindo uma descrição por um controle de estoque, com adição de itens, remoção, calculo de preço do pedido.
- Controle por Role para permitir um admin registrar itens no estoque.
  
### CI/CD
- realizar testes durante package do github actions (não está reconhecendo/conectando ao container no momento)
