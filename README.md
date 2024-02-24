# Controle de Delivery de Restaurante
Este é um projeto simplificado para o controle de delivery de um restaurante, desenvolvido como parte do processo seletivo para a posição de Desenvolvedor Java Back-end.

## Stack Obrigatória
API RESTful
- Java 17
- Spring Boot 2.7.X
- Banco de Dados PostgresSQL
- Maven
- OpenAPI 3.0 (Swagger)

## Funcionalidades
### Segurança
- Permitir o cadastro de usuários e login com autenticação via token JWT. Os métodos das APIs abaixo só poderão ser executados caso o usuário esteja logado.
### Cliente
- Permitir o cadastro, alteração, deleção e consulta de clientes.
### Pedido
- Permitir o cadastro, alteração, deleção e consulta de pedidos. Um pedido obrigatoriamente precisa ter um cliente e um cliente pode ter vários pedidos.
### Entrega
- Permitir o cadastro, alteração, deleção e consulta de entregas. Uma entrega obrigatoriamente necessita estar vinculada a um pedido.

## Instruções para Execução
### Via Web (servidor será desativado após avaliação)
https://acert.up.railway.app/swagger-ui/index.html

### Localmente, sem Docker:
1. Certifique-se de ter instalado em sua máquina: Java 17, PostgresSQL
2. No PostgreSQL, crie um banco de dados para a aplicação.
3. Clone este repositório em seu ambiente local.
4. Configure as credenciais do banco de dados no arquivo application.yml.
5. Execute o comando Maven ```mvn spring-boot:run```
6. A API estará disponível no seguinte endereço: http://localhost:8080/swagger-ui.html


### Localmente, com Docker:
1. Certifique-se de ter instalado e em execução sua máquina: Docker
2. Clone este repositório em seu ambiente local.
3. Execute os seguintes comandos no terminal da IDE (na raiz do projeto):
  - Maven: Gera o artefato ```mvn package```
  - Docker: Utiliza o arquivo DockerFile-dev para gerar a imagem docker ```docker build -t acert-api -f Dockerfile-dev .```
  - Docker: Utiliza o arquivo Docker-Compose para orquestrar os containers da API e do PostgresSQL ```docker-compose up```
4. A API estará disponível no seguinte endereço: http://localhost:8080/swagger-ui.html
