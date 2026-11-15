# language: pt
@formulario
Funcionalidade: Cadastro no formulário grande
  Como usuário do site the-internet
  Quero preencher todos os campos do Large Form
  Para enviar meu cadastro com sucesso

  # Os dados são aleatórios a cada execução. A semente usada aparece no log e no relatório;
  # para repetir uma execução com os mesmos dados: mvn test -Dseed=<valor>

  Cenário: Preencher e enviar o formulário grande com dados aleatórios
    Dado que gerei dados aleatórios para o formulário
    E que estou na página inicial
    E acesso o exemplo "Large Form"
    Quando preencho os dados da conta e os detalhes
    E seleciono os idiomas, a preferência de contato e os interesses
    E defino os anos de experiência e a cor favorita
    E aceito os termos
    E envio o formulário
    Então devo ver a confirmação de envio "Form Submitted"
