# kadu-borges-LC-answer

## Descrição do Projeto
<p align="center">
A solução foi criada utilizando-se os seguintes conceitos e tecnologias:
<ul>
  <li>Spring Boot v2.7.1
    <ul>
      <li>Spring Web</li>
      <li>Spring Data JPA</li>
      <li>Spring Security</li>
      <li>Spring Security OAuth2</li>
      <li>Spring Security JWT</li>
    </ul>
  </li>
  <li>JUnit 5</li>
  <li>Lombok</li>
  <li>SpringDoc</li>
  <li>H2 Database</li>
  <li>Arquitetura Controller-Service-Repository</li>
</ul>
</p>

## Suposições
Como era de se esperar, muitas lacunas foram encontradas nos requisitos do desafio. Para as que foram identificadas, algumas suposições foram feitas para que o andamento dos trabalhos não fosse prejudicado. Seguem:
<ul>
  <li>Enquanto um usuário não encerrar um quiz aberto, ele não poderá criar outro.</li>
  <li>Quizzes abertos não serão contabilizados no ranking.</li>
  <li>A aplicação não considera a existência de filmes que possuam a mesma pontuação.</li>
</ul>

## Para acessar a especificação OpenAPI 3.0
Para acessar a OpenAPI 3.0, basta acessar o link abaixo quando o servidor de recursos estiver ativo:
http://localhost:8080/v3/api-docs


