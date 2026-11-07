# language: pt
@login
Funcionalidade: Login na área segura
  Como usuário do site the-internet
  Quero me autenticar com meu usuário e senha
  Para acessar a área segura

  Contexto:
    Dado que estou na página de login

  @positivo
  Cenário: Login com credenciais válidas
    Quando faço login com credenciais válidas
    Então devo ser direcionado à área segura com a mensagem "You logged into a secure area!"
    E ao acessar a página inicial devo ver o título "Welcome to the-internet"

  @negativo
  Esquema do Cenário: Login com credenciais inválidas - <situacao>
    Quando tento fazer login com o usuário "<usuario>" e a senha "<senha>"
    Então devo ver a mensagem de erro "<mensagem>"

    Exemplos:
      | situacao                  | usuario         | senha                | mensagem                  |
      | senha em branco           | tomsmith        |                      | Your password is invalid! |
      | usuário em branco         |                 | SuperSecretPassword! | Your username is invalid! |
      | usuário e senha inválidos | usuarioInvalido | senhaInvalida        | Your username is invalid! |
