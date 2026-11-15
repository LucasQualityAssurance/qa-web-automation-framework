# language: pt
@carregamento-dinamico
Funcionalidade: Carregamento dinâmico de elementos
  Como usuário do site the-internet
  Quero iniciar um carregamento dinâmico
  Para ver a mensagem que só aparece quando o carregamento termina

  Contexto:
    Dado que estou na página inicial
    E acesso o exemplo "Dynamic Loading"

  Esquema do Cenário: Exibir a mensagem depois do carregamento - <situacao>
    Quando abro o exemplo <exemplo> de carregamento dinâmico
    Então a mensagem final não deve estar visível
    Quando inicio o carregamento
    Então devo ver a mensagem final "Hello World!"

    Exemplos:
      | exemplo | situacao                                |
      | 1       | elemento ja existe na pagina, oculto    |
      | 2       | elemento criado depois do carregamento  |
