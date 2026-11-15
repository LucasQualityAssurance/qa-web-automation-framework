# language: pt
@alertas
Funcionalidade: Alertas JavaScript
  Como usuário do site the-internet
  Quero responder aos alertas nativos do navegador (alert, confirm e prompt)
  Para que a página registre a resposta que eu dei

  Contexto:
    Dado que estou na página inicial
    E acesso o exemplo "JavaScript Alerts"

  @alerta
  Cenário: Aceitar o alerta simples
    Quando abro o alerta pelo botão "Click for JS Alert"
    Então o alerta deve exibir o texto "I am a JS Alert"
    Quando aceito o alerta
    Então devo ver o resultado "You successfully clicked an alert"

  @confirmacao
  Cenário: Confirmar a caixa de confirmação
    Quando abro o alerta pelo botão "Click for JS Confirm"
    Então o alerta deve exibir o texto "I am a JS Confirm"
    Quando aceito o alerta
    Então devo ver o resultado "You clicked: Ok"

  @confirmacao
  Cenário: Cancelar a caixa de confirmação
    Quando abro o alerta pelo botão "Click for JS Confirm"
    E cancelo o alerta
    Então devo ver o resultado "You clicked: Cancel"

  @prompt
  Cenário: Digitar um texto no prompt e confirmar
    Quando abro o alerta pelo botão "Click for JS Prompt"
    Então o alerta deve exibir o texto "I am a JS prompt"
    Quando digito "Teste de automacao" no alerta e o aceito
    Então devo ver o resultado "You entered: Teste de automacao"

  @prompt
  Cenário: Cancelar o prompt sem informar texto
    Quando abro o alerta pelo botão "Click for JS Prompt"
    E cancelo o alerta
    Então devo ver o resultado "You entered: null"
