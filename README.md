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
### Localmente, sem Docker:
1. Certifique-se de ter o Java 17 instalado em sua máquina: Java 17, PostgresSQL
2. No PostgreSQL, crie um banco de dados para a aplicação.
3. Clone este repositório em seu ambiente local.
4. Configure as credenciais do banco de dados no arquivo application.yml.
5. Execute o comando mvn spring-boot:run na raiz do projeto para iniciar a aplicação.
6. A API estará disponível no seguinte endereço: http://localhost:8080/swagger-ui.html
