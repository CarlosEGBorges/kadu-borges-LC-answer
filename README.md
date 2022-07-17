# LC Challenge - Kadu Borges' answer

## Descrição do Projeto
<p align="center">
A solução foi criada utilizando-se os seguintes conceitos e tecnologias:
<ul>
  <li>Spring Boot v2.7.1
    <ul>
      <li>Spring Web</li>
      <li>Spring Data JPA</li>
      <li>Spring Security com OAuth2 e JWT</li>
    </ul>
  </li>
  <li>JUnit 5</li>
  <li>Lombok</li>
  <li>SpringDoc</li>
  <li>H2 Database</li>
  <li>Arquitetura
    <ul>
      <li>Authorization Server</li>
      <li>Resource Server (Controller-Service-Repository)</li>
    </ul>
  </li>
</ul>
</p>

## Suposições
Como era de se esperar de um desafio, muitas lacunas nas regras de negócio podem ser identificadas. Por isso, diante delas, algumas suposições foram feitas para que o andamento dos trabalhos não fosse prejudicado. Seguem:
<ul>
  <li>Um novo quiz só poderá ser criado se todos os quizzes anteriores estiverem encerrados.</li>
  <li>Quizzes abertos não serão contabilizados nos cálculos do ranking.</li>
  <li>A aplicação não considera a existência de filmes que possuam a mesma pontuação.</li>
  <li>Se um quiz for encerrado com uma questão aberta, esta será considerada como errada.</li>
</ul>

## Definição OpenAPI 3.0
Para acessar a OpenAPI 3.0, basta acessar o link abaixo quando o servidor de recursos estiver ativo:
http://localhost:8080/v3/api-docs


