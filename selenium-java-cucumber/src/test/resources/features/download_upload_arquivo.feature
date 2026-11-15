# language: pt
@arquivos
Funcionalidade: Download e upload de arquivos
  Como usuário do site the-internet
  Quero baixar um arquivo e depois enviá-lo de volta
  Para confirmar que o site recebe o mesmo arquivo que eu baixei

  # A pasta de downloads é esvaziada antes de cada cenário com a tag @arquivos.
  # O arquivo baixado é um dos que o site sempre traz, o que mantém o cenário determinístico.

  Cenário: Baixar um arquivo e enviá-lo pelo upload
    Dado que estou na página inicial
    E acesso o exemplo "File Download"
    Quando baixo o arquivo "some-file.txt"
    Então o arquivo deve ser salvo na pasta de downloads com conteúdo
    Quando volto para a página inicial
    E acesso o exemplo "File Upload"
    E envio o arquivo baixado pelo upload
    Então devo ver a confirmação de upload "File Uploaded!"
    E o nome do arquivo enviado deve ser o do arquivo baixado
