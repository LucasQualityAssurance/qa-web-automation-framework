# selenium-java

[![selenium-java](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/selenium-java.yml/badge.svg)](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/selenium-java.yml)

> Parte do repositório [qa-web-automation-framework](../README.md). Para a versão BDD, veja [`selenium-java-cucumber`](../selenium-java-cucumber).

Framework de automação de testes Web construído com **Java 21**, **Selenium 4** e **JUnit 5**,
seguindo o padrão **Page Object Model**, com relatório no **Allure**.

O sistema testado é o [The Internet](https://the-internet.herokuapp.com), um site open source da
Sauce Labs feito para praticar automação ([código-fonte](https://github.com/saucelabs/the-internet)).
Ele reúne vários exemplos de situações reais (login, formulários, downloads, alertas, janelas,
elementos dinâmicos...), o que permite exercitar as principais técnicas de automação Web.

## Sumário

- [Tecnologias](#tecnologias)
- [Cenários de teste](#cenários-de-teste)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Como rodar na sua máquina](#como-rodar-na-sua-máquina)
- [Opções de execução](#opções-de-execução)
- [Rodando pelo IntelliJ IDEA](#rodando-pelo-intellij-idea)
- [Relatório Allure](#relatório-allure)
- [Integração contínua (GitHub Actions)](#integração-contínua-github-actions)
- [Boas práticas adotadas](#boas-práticas-adotadas)
- [Como criar um novo teste](#como-criar-um-novo-teste)
- [Solução de problemas](#solução-de-problemas)
- [Licença](#licença)

## Tecnologias

| Ferramenta | Versão | Para que serve |
|---|---|---|
| Java (JDK) | 21 | Linguagem dos testes |
| Selenium WebDriver | 4.38.0 | Controle do navegador. O driver do Chrome é baixado automaticamente pelo Selenium Manager |
| JUnit 5 (Jupiter) | 5.13.4 | Execução dos testes, incluindo testes parametrizados |
| Maven | 3.9+ | Build, dependências e execução pela linha de comando |
| Allure Report | 2.35.1 | Relatório com os passos de cada teste, agrupados por funcionalidade |
| Google Chrome | atual | Navegador usado nos testes |

## Cenários de teste

| Classe de teste | Funcionalidade | O que é validado |
|---|---|---|
| `LoginTest` | Login | Login com credenciais válidas e retorno para a página inicial |
| `LoginCredenciaisInvalidasTest` | Login | Parametrizado: senha em branco, usuário em branco, usuário e senha inválidos, conferindo a mensagem de erro |
| `CadastroFormularioTest` | Formulário | Preenche todos os campos do Large Form com dados aleatórios e envia |
| `DownloadUploadArquivoTest` | Download e Upload | Baixa um arquivo do site para o projeto e faz o upload desse mesmo arquivo |
| `CarregamentoDinamicoTest` | Carregamento Dinâmico | Parametrizado: aguarda a mensagem "Hello World!" nos dois exemplos (elemento oculto e elemento criado depois) |
| `AlertasJavaScriptTest` | Alertas JavaScript | Alert com OK, Confirm com OK e Cancel, Prompt com texto e cancelado |
| `MultiplasJanelasTest` | Múltiplas Janelas | Abre uma nova aba, troca o foco para ela, fecha e volta para a janela original |

Os nomes dos testes, os logs dos passos e os métodos dos page objects estão em português. As
validações comparam os textos originais do site, que estão em inglês.

## Estrutura do projeto

O projeto Maven fica na pasta `selenium-java/`, dentro do repositório:

```
qa-web-automation-framework/
├── README.md
├── LICENSE
├── Files/                                  # Arquivos baixados pelos testes (criada na execução, ignorada pelo Git)
└── selenium-java/
    ├── pom.xml                             # Dependências e plugins (Surefire e Allure)
    └── src
        ├── main/java
        │   ├── factory/DriverFactory.java  # Cria o Chrome (pasta de downloads, modo headless)
        │   ├── pages/                      # Page objects: BasePage + uma classe por página
        │   └── utils/                      # ConfigReader, TestLogger, ArquivoUtils, AllureAmbiente
        └── test
            ├── java
            │   ├── base/BaseTest.java      # Abre e fecha o navegador em cada teste
            │   ├── dados/                  # Massa de teste (credenciais e gerador de dados aleatórios)
            │   └── tests/                  # Classes de teste
            └── resources/allure.properties # Pasta onde o Allure grava os resultados
```

Como as peças se encaixam:

- **`BaseTest`**: toda classe de teste herda dele. Antes de cada teste ele abre um Chrome novo e,
  no fim, fecha o navegador. Assim os testes são independentes entre si.
- **Page objects (`pages/`)**: cada página do site tem uma classe com seus localizadores e ações
  (`abrir()`, `fazerLogin()`, `enviar()`...). Os testes só conversam com essas classes, nunca
  com o Selenium diretamente.
- **`BasePage`**: concentra as interações comuns (navegar, clicar, digitar, ler texto, aguardar
  alerta), sempre com espera explícita.
- **`TestLogger`**: registra cada passo no terminal com o prefixo `[STEP]` e no relatório Allure.
- **`ConfigReader`**: centraliza as configurações (URL do site, tempos de espera, pasta de downloads,
  modo headless, pausa entre passos).

## Pré-requisitos

Você vai precisar de:

| Programa | Versão | Observação |
|---|---|---|
| Git | qualquer | Para clonar o repositório |
| JDK | 21 | Precisa ser o **JDK** (com compilador), não só o JRE |
| Maven | 3.9 ou superior | Não é necessário se você for rodar só pelo IntelliJ, que já traz um Maven embutido |
| Google Chrome | versão atual | O driver compatível é baixado automaticamente na primeira execução |

Também é preciso ter **acesso à internet**: os testes acessam o site
https://the-internet.herokuapp.com e, na primeira execução, o Maven baixa as dependências e o
Selenium Manager baixa o ChromeDriver.

### Instalando no Windows

1. **JDK 21**: instale pelo terminal com
   ```powershell
   winget install Microsoft.OpenJDK.21
   ```
   ou baixe o instalador em [Adoptium (Temurin 21)](https://adoptium.net/temurin/releases/?version=21).
   Marque a opção de configurar o `JAVA_HOME`, se o instalador oferecer.
2. **Maven**: baixe o arquivo `apache-maven-3.9.x-bin.zip` em
   [maven.apache.org/download.cgi](https://maven.apache.org/download.cgi), extraia (por exemplo em
   `C:\Ferramentas\apache-maven-3.9.x`) e adicione a pasta `bin` ao `PATH`:
   *Iniciar → "Editar as variáveis de ambiente do sistema" → Variáveis de Ambiente → Path → Editar → Novo*.
3. **JAVA_HOME**: se ele não tiver sido configurado na instalação, crie a variável de ambiente
   `JAVA_HOME` apontando para a pasta do JDK (ex.: `C:\Program Files\Microsoft\jdk-21...`).
4. Feche e abra o terminal de novo para as variáveis valerem.

### Instalando no macOS

Com o [Homebrew](https://brew.sh):

```bash
brew install openjdk@21 maven
```

Siga a instrução que o Homebrew mostra no fim da instalação para ligar o `openjdk@21` ao sistema.

### Instalando no Linux

No Ubuntu/Debian:

```bash
sudo apt update
sudo apt install openjdk-21-jdk maven
```

Se a versão do Maven do seu sistema for anterior à 3.9, instale uma mais nova pelo
[SDKMAN!](https://sdkman.io) (`sdk install maven`) ou pelo site oficial.

### Conferindo a instalação

```bash
java -version    # deve mostrar a versão 21
javac -version   # deve mostrar a versão 21 (se não existir, você instalou só o JRE)
mvn -version     # deve mostrar Maven 3.9+ e "Java version: 21"
git --version
```

## Como rodar na sua máquina

### 1. Clonar o repositório

```bash
git clone https://github.com/LucasQualityAssurance/qa-web-automation-framework.git
cd qa-web-automation-framework/selenium-java
```

> Todos os comandos `mvn` devem ser executados dentro da pasta `selenium-java`, que é onde fica o
> `pom.xml`. Rodando de outra pasta, o Maven mostra o erro "there is no POM in this directory".

### 2. Rodar todos os testes

```bash
mvn test
```

Na primeira vez o Maven baixa as dependências, o que pode levar alguns minutos. O Chrome abre na
sua tela e cada passo espera 2 segundos, para que você consiga acompanhar a automação.

Durante a execução, cada passo aparece no terminal:

```
[STEP] Iniciando teste: Deve abrir uma nova janela e voltar para a original
[STEP] Abrindo a home page (/)
[STEP] Clicando no link 'Multiple Windows'
[STEP] Clicando em 'Click Here' e trocando para a nova aba
...
[INFO] Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### 3. Rodar sem abrir o navegador (modo headless)

```bash
mvn test -Dheadless=true
```

O Chrome roda em segundo plano, sem janela e sem a pausa entre os passos. É o modo mais rápido e o
indicado para servidores de integração contínua (CI).

### 4. Rodar só uma classe ou um único teste

```bash
# Uma classe
mvn test -Dtest=LoginTest

# Mais de uma classe
mvn test -Dtest=LoginTest,MultiplasJanelasTest

# Um único método de uma classe
mvn test -Dtest=AlertasJavaScriptTest#deveAceitarAlertaSimples
```

## Opções de execução

Todas as opções são passadas com `-D` e podem ser combinadas
(ex.: `mvn test -Dtest=LoginTest -DpausaEntrePassos=0`).

| Opção | Padrão | O que faz |
|---|---|---|
| `-Dheadless=true` | `false` | Roda o Chrome sem abrir janela |
| `-DpausaEntrePassos=<segundos>` | `2` com janela, `0` em headless | Pausa depois de cada passo, para acompanhar a execução na tela. Use `0` para rodar na velocidade normal |
| `-Dtest=<Classe>` ou `<Classe>#<método>` | todos | Escolhe quais testes rodar |
| `-Dseed=<número>` | aleatório | Repete os mesmos dados aleatórios de uma execução anterior do teste de formulário (veja abaixo) |
| `-DdiretorioDownload=<caminho>` | `Files/` na raiz do repositório | Muda a pasta onde o Chrome salva os downloads |

### Pausa entre os passos

A pausa serve **só para visualização**. A sincronização dos testes é feita com esperas explícitas
(o Selenium aguarda o elemento ficar visível ou clicável), então os testes funcionam igual com
`-DpausaEntrePassos=0`.

### Dados aleatórios do formulário

O `CadastroFormularioTest` preenche o formulário com dados diferentes a cada execução (nome, e-mail,
senha, data de nascimento, país, idiomas, interesses, cor...), gerados pela classe
`dados/DadosFormulario`. Todos os valores vêm de uma única **semente**, mostrada no primeiro passo:

```
[STEP] Dados aleatorios gerados (para repetir: -Dseed=235579287892700)
```

Se o teste falhar com algum dado, repita a execução com exatamente os mesmos valores:

```bash
mvn test -Dtest=CadastroFormularioTest -Dseed=235579287892700
```

A data de nascimento é calculada a partir do dia atual, então ela só se repete igual no mesmo dia.

### Arquivos baixados

O Chrome salva os downloads na pasta `Files/`, na raiz do repositório. Ela é limpa antes do teste
de download e é ignorada pelo Git.

## Rodando pelo IntelliJ IDEA

1. **File → Open** e selecione a pasta `selenium-java` (a que contém o `pom.xml`). Se o IntelliJ
   perguntar, escolha abrir como projeto Maven e clique em **Trust Project**.
2. Aguarde o IntelliJ baixar as dependências (barra de progresso no rodapé).
3. Confira o JDK em **File → Project Structure → Project → SDK**: deve ser um JDK 21. Se não houver,
   use **Add SDK → Download JDK** e escolha a versão 21.
4. Abra uma classe em `src/test/java/tests` e clique no ícone ▶ verde ao lado da classe ou do método.
   Para rodar tudo, clique com o botão direito na pasta `tests` → **Run 'Tests in 'tests''**.

Para usar as opções de execução no IntelliJ, abra **Run → Edit Configurations…** e coloque-as em
**VM options** (ex.: `-Dheadless=true -DpausaEntrePassos=0`).

Se arquivos novos não aparecerem na árvore do projeto, use **File → Reload All from Disk**
(`Ctrl+Alt+Y`) e, na aba **Maven**, clique em **Reload All Maven Projects**.

## Relatório Allure

Os testes gravam os resultados em `selenium-java/target/allure-results`. Para ver o relatório:

```bash
mvn clean test       # roda os testes e grava os resultados
mvn allure:serve     # gera o relatório e abre no navegador
```

Não é preciso instalar o Allure: o plugin do Maven baixa a ferramenta na primeira vez. Para parar o
servidor do relatório, use `Ctrl+C` no terminal.

O relatório mostra:

- cada teste com seus passos (`[STEP]`) e o resultado;
- os testes agrupados por classe de teste, na aba **Suites**;
- o ambiente da execução (URL, navegador, modo headless, versão do Java e sistema operacional).

> Use `mvn clean test` antes de gerar o relatório: sem o `clean`, os resultados das execuções
> anteriores continuam na pasta e aparecem misturados no relatório.

## Integração contínua (GitHub Actions)

Os testes rodam automaticamente no GitHub Actions pelo workflow
[`.github/workflows/selenium-java.yml`](../.github/workflows/selenium-java.yml). O selo no topo deste
README mostra o resultado da última execução.

**Quando roda:**

- a cada push na `main` que altere a pasta `selenium-java/` ou o próprio workflow;
- a cada pull request para a `main` com essas mesmas alterações;
- manualmente, na aba **Actions → selenium-java → Run workflow**.

**O que faz:**

| Etapa | Comando / ação |
|---|---|
| Baixar o código | `actions/checkout` |
| Instalar o JDK 21 | `actions/setup-java` (Temurin), com cache das dependências do Maven |
| Rodar os testes | `mvn -B clean test -Dheadless=true` (Chrome sem janela, em Ubuntu) |
| Gerar o relatório Allure | `mvn -B allure:report`, executado mesmo quando algum teste falha |
| Publicar o relatório | Artefato `allure-report`, disponível por 14 dias |

**Como ver o relatório de uma execução:**

1. Abra a aba **Actions** do repositório e clique na execução desejada.
2. No fim da página, em **Artifacts**, baixe o `allure-report` e extraia o `.zip`.
3. Abra o relatório com um servidor local, dentro da pasta extraída:
   ```bash
   npx http-server
   ```
   Depois acesse o endereço mostrado no terminal (ex.: http://127.0.0.1:8080). Abrir o
   `index.html` direto no navegador não funciona, porque o navegador bloqueia o carregamento dos
   dados do relatório a partir de arquivos locais.

No CI o Chrome já vem instalado na máquina do GitHub, e o Selenium Manager baixa o ChromeDriver
compatível, então nenhuma configuração extra é necessária.

## Boas práticas adotadas

- **Page Object Model**: localizadores e ações ficam nas páginas; os testes descrevem o comportamento.
- **Esperas explícitas**: nenhuma sincronização é feita com pausas fixas. O Selenium aguarda o elemento
  ficar visível ou clicável, a janela abrir ou o alerta aparecer.
- **Testes independentes**: cada teste abre o próprio navegador e começa da página inicial, então podem
  rodar em qualquer ordem ou sozinhos.
- **Dados controlados**: os downloads usam arquivos fixos do site, e os dados aleatórios podem ser
  reproduzidos com a semente.
- **Testes parametrizados**: cenários parecidos (como os de login inválido) ficam em uma tabela com
  `@ParameterizedTest`, em vez de métodos repetidos.
- **Localizadores estáveis**: preferência por `id`, `name` e `href` em vez de textos visíveis ou XPath longos.
- **Rastreabilidade**: cada passo aparece no terminal e no relatório, facilitando entender onde um teste falhou.

## Como criar um novo teste

1. **Crie o page object** em `src/main/java/pages`, herdando de `BasePage`: declare os localizadores
   (`By`) e os métodos das ações da página, usando `clicar`, `escrever`, `obterTexto`...
2. **Adicione o link na `HomePage`** (ex.: `acessarMinhaPagina()`), localizado pelo `href`.
3. **Crie a classe de teste** em `src/test/java/tests`, herdando de `BaseTest`, com
   `@Test` e `@DisplayName`.
4. **Registre os passos** com `TestLogger.step("...")` antes de cada ação. Para mensagens que não
   envolvem ação na tela, use `TestLogger.info("...")`, que não faz pausa.
5. Rode com `mvn test -Dtest=NomeDaClasseTest`.

## Solução de problemas

| Problema | Causa provável | Como resolver |
|---|---|---|
| `mvn: command not found` / `'mvn' não é reconhecido` | Maven não instalado ou fora do `PATH` | Instale o Maven e adicione a pasta `bin` ao `PATH`, ou rode pelo IntelliJ |
| `No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?` | O `JAVA_HOME` aponta para um JRE | Instale o JDK 21 e aponte o `JAVA_HOME` para ele. Confira com `javac -version` |
| `there is no POM in this directory` | Comando executado fora da pasta do projeto | Entre na pasta `selenium-java` antes de rodar o `mvn` |
| `release version 21 not supported` | Maven rodando com Java mais antigo | Confira com `mvn -version` se o Java é o 21 e ajuste o `JAVA_HOME` |
| Aviso `Unable to find CDP implementation matching ...` | O Chrome é mais novo que o suporte a DevTools do Selenium | É só um aviso e não afeta os testes |
| Erro ao iniciar o Chrome / `SessionNotCreatedException` | Chrome não instalado, ou sem internet para baixar o driver | Instale ou atualize o Google Chrome e confira a conexão na primeira execução |
| Timeout esperando um elemento | Site lento ou fora do ar | Abra https://the-internet.herokuapp.com no navegador e rode de novo |
| Testes novos não aparecem no IntelliJ | Árvore do projeto desatualizada | **File → Reload All from Disk** e **Reload All Maven Projects** |

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](../LICENSE).
