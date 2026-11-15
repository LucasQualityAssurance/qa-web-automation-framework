# language: pt
@janelas
Funcionalidade: Múltiplas janelas
  Como usuário do site the-internet
  Quero abrir um link em uma nova aba e depois fechá-la
  Para continuar navegando na janela original

  Contexto:
    Dado que estou na página inicial
    E acesso o exemplo "Multiple Windows"

  Cenário: Abrir uma nova janela e voltar para a original
    Quando clico no link que abre uma nova janela
    Então devo ter 2 janelas abertas
    E a nova janela deve exibir o título "New Window" na aba e na página
    Quando fecho a nova janela e volto para a original
    Então devo ter 1 janela aberta
    E a janela original deve exibir o título "Opening a new window"
