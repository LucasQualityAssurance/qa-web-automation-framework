# selenium-java-cucumber

> Parte do repositório [qa-web-automation-framework](../README.md). Para a versão com testes 100% em
> Java (JUnit 5), veja [`selenium-java`](../selenium-java).

Framework de automação de testes Web no estilo **BDD (Behavior-Driven Development)**, construído com
**Java 21**, **Selenium 4** e **Cucumber 7**, seguindo o padrão **Page Object Model**, com relatório
no **Allure**.

Os cenários são escritos em **Gherkin em português** (`Dado / Quando / Então`), em arquivos `.feature`
que qualquer pessoa do time consegue ler, e cada passo é ligado a um método Java (step definition)
que usa os page objects para controlar o navegador.

O sistema testado é o [The Internet](https://the-internet.herokuapp.com), um site open source da
Sauce Labs feito para praticar automação ([código-fonte](https://github.com/saucelabs/the-internet)).

## Sumário

- [Tecnologias](#tecnologias)
- [Cenários de teste](#cenários-de-teste)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Como rodar na sua máquina](#como-rodar-na-sua-máquina)
- [Rodando só alguns cenários](#rodando-só-alguns-cenários)
- [Opções de execução](#opções-de-execução)
- [Rodando pelo IntelliJ IDEA](#rodando-pelo-intellij-idea)
- [Relatórios](#relatórios)
- [Boas práticas adotadas](#boas-práticas-adotadas)
- [Como criar um novo cenário](#como-criar-um-novo-cenário)
- [Solução de problemas](#solução-de-problemas)
- [Licença](#licença)

## Tecnologias

| Ferramenta | Versão | Para que serve |
|---|---|---|
| Java (JDK) | 21 | Linguagem das step definitions e dos page objects |
| Selenium WebDriver | 4.52.0 | Controle do navegador. O driver do Chrome é baixado automaticamente pelo Selenium Manager |
| Cucumber | 7.20.1 | Lê os arquivos `.feature` e executa os cenários em Gherkin |
| JUnit Platform | 5.13.4 | Executa o Cucumber pelo `mvn test` e pelo IntelliJ (`cucumber-junit-platform-engine` + `@Suite`) |
| Maven | 3.9+ | Build, dependências e execução pela linha de comando |
| Allure Report | 2.35.1 | Relatório com os passos de cada cenário, agrupados por funcionalidade |
| Google Chrome | atual | Navegador usado nos testes |

## Cenários de teste

São **6 funcionalidades** e **14 cenários**, em `src/test/resources/features`:

| Feature | Tags | Cenários |
|---|---|---|
| `login.feature` | `@login` `@positivo` `@negativo` | Login com credenciais válidas e retorno para a página inicial; Esquema do Cenário com senha em branco, usuário em branco e usuário e senha inválidos, conferindo a mensagem de erro |
| `alertas_javascript.feature` | `@alertas` `@alerta` `@confirmacao` `@prompt` | Alert com OK, Confirm com OK e com Cancel, Prompt com texto e cancelado |
| `cadastro_formulario.feature` | `@formulario` | Preenche todos os campos do Large Form com dados aleatórios e envia |
| `carregamento_dinamico.feature` | `@carregamento-dinamico` | Esquema do Cenário: aguarda a mensagem "Hello World!" nos dois exemplos (elemento oculto e elemento criado depois) |
| `download_upload_arquivo.feature` | `@arquivos` | Baixa um arquivo do site para o projeto e faz o upload desse mesmo arquivo |
| `multiplas_janelas.feature` | `@janelas` | Abre uma nova aba, troca o foco para ela, fecha e volta para a janela original |

Exemplo de cenário (`login.feature`):

```gherkin
# language: pt
@login
Funcionalidade: Login na área segura
  Como usuário do site the-internet
  Quero me autenticar com meu usuário e senha
  Para acessar a área segura

  Contexto:
    Dado que estou na página de login

  @negativo
  Esquema do Cenário: Login com credenciais inválidas - <situacao>
    Quando tento fazer login com o usuário "<usuario>" e a senha "<senha>"
    Então devo ver a mensagem de erro "<mensagem>"

    Exemplos:
      | situacao                  | usuario         | senha                | mensagem                  |
      | senha em branco           | tomsmith        |                      | Your password is invalid! |
      | usuario em branco         |                 | SuperSecretPassword! | Your username is invalid! |
      | usuario e senha invalidos | usuarioInvalido | senhaInvalida        | Your username is invalid! |
```

A linha `# language: pt` faz o Cucumber aceitar as palavras-chave em português (`Funcionalidade`,
`Contexto`, `Cenário`, `Esquema do Cenário`, `Exemplos`, `Dado`, `Quando`, `Então`, `E`). As
validações comparam os textos originais do site, que estão em inglês.

## Estrutura do projeto

O projeto Maven fica na pasta `selenium-java-cucumber/`, dentro do repositório:

```
qa-web-automation-framework/
├── Files/                                      # Arquivos baixados pelos testes (criada na execução, ignorada pelo Git)
└── selenium-java-cucumber/
    ├── pom.xml                                 # Dependências e plugins (Surefire e Allure)
    └── src
        ├── main/java
        │   ├── factory/DriverFactory.java      # Cria o Chrome (pasta de downloads, modo headless)
        │   ├── pages/                          # Page objects: BasePage + uma classe por página
        │   └── utils/                          # ConfigReader, TestLogger, ArquivoUtils, AllureAmbiente
        └── test
            ├── java
            │   ├── dados/                      # Massa de teste (credenciais e gerador de dados aleatórios)
            │   ├── hooks/
            │   │   ├── Hooks.java              # Abre e fecha o navegador em cada cenário, evidência da falha
            │   │   └── PausaEntrePassosPlugin.java  # Pausa de visualização depois de cada passo
            │   ├── runner/
            │   │   └── TodosOsCenariosCucumberTest.java  # Runner: liga as features, os steps e os plugins
            │   └── steps/                      # Step definitions: uma classe por feature + NavegacaoSteps
            └── resources
                ├── features/                   # Cenários em Gherkin (.feature)
                └── allure.properties           # Pasta onde o Allure grava os resultados
```

Como as peças se encaixam:

- **Features (`features/`)**: descrevem o comportamento esperado em linguagem de negócio. Não têm
  código nem detalhes técnicos de tela.
- **Step definitions (`steps/`)**: cada frase do Gherkin é ligada a um método Java por uma anotação
  (`@Dado`, `@Quando`, `@Entao`, `@E`). Os textos entre aspas e os números viram parâmetros do método
  (`{string}`, `{int}`).
- **`NavegacaoSteps`**: passos comuns a todas as features, como `Dado que estou na página inicial` e
  `E acesso o exemplo "JavaScript Alerts"`.
- **`Hooks`**: antes de cada cenário abre um Chrome novo e, no fim, fecha o navegador. Se o cenário
  falhar, anexa um screenshot ao relatório. O driver fica em um `ThreadLocal` e é obtido pelos steps
  com `Hooks.getDriver()`.
- **`PausaEntrePassosPlugin`**: faz a pausa de visualização depois de cada passo. É um plugin do
  Cucumber, e não um hook, para não aparecer como um passo extra no relatório.
- **Runner (`TodosOsCenariosCucumberTest`)**: diz à JUnit Platform para rodar o Cucumber com as
  features de `features/`, os steps de `hooks` e `steps` e os plugins de relatório.
- **Page objects (`pages/`)**: cada página do site tem uma classe com seus localizadores e ações. Os
  steps só conversam com essas classes, nunca com o Selenium diretamente.

O Cucumber cria uma nova instância de cada classe de steps a cada cenário, então o estado guardado
nelas (páginas abertas, alerta, arquivo baixado) não passa de um cenário para outro.

## Pré-requisitos

| Programa | Versão | Observação |
|---|---|---|
| Git | qualquer | Para clonar o repositório |
| JDK | 21 | Precisa ser o **JDK** (com compilador), não só o JRE |
| Maven | 3.9 ou superior | Não é necessário se você for rodar só pelo IntelliJ, que já traz um Maven embutido |
| Google Chrome | versão atual | O driver compatível é baixado automaticamente na primeira execução |

Também é preciso ter **acesso à internet**: os testes acessam o site
https://the-internet.herokuapp.com e, na primeira execução, o Maven baixa as dependências e o
Selenium Manager baixa o ChromeDriver.

O passo a passo de instalação do JDK e do Maven no Windows, macOS e Linux está no
[README do `selenium-java`](../selenium-java/README.md#pré-requisitos): é o mesmo para os dois projetos.

## Como rodar na sua máquina

### 1. Clonar o repositório

```bash
git clone https://github.com/LucasQualityAssurance/qa-web-automation-framework.git
cd qa-web-automation-framework/selenium-java-cucumber
```

> Todos os comandos `mvn` devem ser executados dentro da pasta `selenium-java-cucumber`, que é onde
> fica o `pom.xml`. Rodando de outra pasta, o Maven mostra o erro "there is no POM in this directory".

### 2. Rodar todos os cenários

```bash
mvn test
```

Na primeira vez o Maven baixa as dependências, o que pode levar alguns minutos. O Chrome abre na
sua tela e cada passo espera 2 segundos, para que você consiga acompanhar a automação.

Durante a execução, cada cenário aparece no terminal com seus passos e o método Java ligado a cada um:

```
@janelas
Cenário: Abrir uma nova janela e voltar para a original                # features/multiplas_janelas.feature:12
[STEP] Iniciando cenario: Abrir uma nova janela e voltar para a original
  Dado que estou na página inicial                                     # steps.NavegacaoSteps.queEstouNaPaginaInicial()
  E acesso o exemplo "Multiple Windows"                                # steps.NavegacaoSteps.acessoOExemplo(java.lang.String)
  Quando clico no link que abre uma nova janela                        # steps.MultiplasJanelasSteps.clicoNoLinkQueAbreUmaNovaJanela()
  Então devo ter 2 janelas abertas                                     # steps.MultiplasJanelasSteps.devoTerJanelasAbertas(int)
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

## Rodando só alguns cenários

Há três formas de escolher os cenários. Elas podem ser combinadas com `-Dheadless=true` e as outras
[opções de execução](#opções-de-execução).

> **No PowerShell (Windows)**, coloque cada opção `-D...` inteira entre aspas, como nos exemplos.
> Sem as aspas, o PowerShell quebra o argumento no ponto e o Maven não reconhece a opção.

### Por tag (o mais prático)

```bash
mvn test "-Dcucumber.filter.tags=@janelas"
mvn test "-Dcucumber.filter.tags=@login and @negativo"
mvn test "-Dcucumber.filter.tags=@alertas and not @prompt"
mvn test "-Dcucumber.filter.tags=@formulario or @arquivos"
```

As tags disponíveis estão na tabela de [cenários de teste](#cenários-de-teste).

### Pelo arquivo e pela linha do cenário

```bash
mvn test "-Dcucumber.features=classpath:features/alertas_javascript.feature:13"
```

O número depois dos dois pontos é a linha do `Cenário:` no arquivo. Sem `:linha`, roda a feature inteira.

### Pelo nome do cenário

```bash
mvn test "-Dcucumber.filter.name=.*Cancelar o prompt.*"
```

O valor é uma expressão regular que precisa casar com o **nome inteiro** do cenário, por isso os
`.*` no começo e no fim. Sem eles, nenhum cenário roda.

Nos filtros por tag e por nome, os cenários que ficaram de fora aparecem como `Skipped` no resumo
do Maven. Isso é esperado: só os cenários escolhidos são executados.

## Opções de execução

Todas as opções são passadas com `-D` e podem ser combinadas
(ex.: `mvn test "-Dcucumber.filter.tags=@login" -DpausaEntrePassos=0`).

| Opção | Padrão | O que faz |
|---|---|---|
| `-Dheadless=true` | `false` | Roda o Chrome sem abrir janela |
| `-DpausaEntrePassos=<segundos>` | `2` com janela, `0` em headless | Pausa depois de cada passo, para acompanhar a execução na tela. Use `0` para rodar na velocidade normal |
| `-Dcucumber.filter.tags=<expressão>` | todos | Roda só os cenários com as tags informadas |
| `-Dcucumber.features=<feature>[:linha]` | todas | Roda uma feature ou um único cenário pela linha |
| `-Dcucumber.filter.name=<regex>` | todos | Roda só os cenários cujo nome casa com a expressão regular |
| `-Dseed=<número>` | aleatório | Repete os mesmos dados aleatórios de uma execução anterior do cenário de formulário (veja abaixo) |
| `-DdiretorioDownload=<caminho>` | `Files/` na raiz do repositório | Muda a pasta onde o Chrome salva os downloads |

### Pausa entre os passos

A pausa serve **só para visualização**. A sincronização dos testes é feita com esperas explícitas
(o Selenium aguarda o elemento ficar visível ou clicável), então os cenários funcionam igual com
`-DpausaEntrePassos=0`. Passos pulados depois de uma falha não fazem pausa.

### Dados aleatórios do formulário

O cenário de `cadastro_formulario.feature` preenche o formulário com dados diferentes a cada execução
(nome, e-mail, senha, data de nascimento, país, idiomas, interesses, cor...), gerados pela classe
`dados/DadosFormulario`. Todos os valores vêm de uma única **semente**, mostrada no terminal e no
relatório:

```
[STEP] Dados aleatorios gerados (para repetir: -Dseed=50885498744400)
```

Se o cenário falhar com algum dado, repita a execução com exatamente os mesmos valores:

```bash
mvn test "-Dcucumber.filter.tags=@formulario" "-Dseed=50885498744400"
```

A data de nascimento é calculada a partir do dia atual, então ela só se repete igual no mesmo dia.

### Arquivos baixados

O Chrome salva os downloads na pasta `Files/`, na raiz do repositório. Ela é esvaziada antes de cada
cenário com a tag `@arquivos` (por um hook `@Before("@arquivos")`) e é ignorada pelo Git.

## Rodando pelo IntelliJ IDEA

1. **File → Open** e selecione a pasta `selenium-java-cucumber` (a que contém o `pom.xml`). Se o
   IntelliJ perguntar, escolha abrir como projeto Maven e clique em **Trust Project**.
2. Instale os plugins **Gherkin** e **Cucumber for Java** em **Settings → Plugins**. Eles colorem os
   arquivos `.feature`, ligam cada passo ao método Java (`Ctrl+clique` no passo) e mostram os passos
   que ainda não têm step definition.
3. Confira o JDK em **File → Project Structure → Project → SDK**: deve ser um JDK 21.
4. Para rodar **todos os cenários**, abra `src/test/java/runner/TodosOsCenariosCucumberTest.java` e
   clique no ícone ▶ verde ao lado da classe.
5. Para rodar **uma feature ou um cenário**, abra o arquivo `.feature` e clique no ícone ▶ ao lado
   da `Funcionalidade:` ou do `Cenário:`.

Para usar as opções de execução no IntelliJ, abra **Run → Edit Configurations…** e coloque-as em
**VM options** (ex.: `-Dheadless=true -DpausaEntrePassos=0`).

> Ao rodar direto pelo `.feature`, o IntelliJ cria uma configuração própria do Cucumber, que não usa
> o runner. Se aparecer o erro `WebDriver nao iniciado`, o IntelliJ não encontrou os hooks: em
> **Run → Edit Configurations… → Edit configuration templates → Cucumber Java**, preencha o campo
> **Glue** com `hooks steps`.

## Relatórios

### Allure

Os cenários gravam os resultados em `selenium-java-cucumber/target/allure-results`. Para ver o relatório:

```bash
mvn clean test       # roda os cenários e grava os resultados
mvn allure:serve     # gera o relatório e abre no navegador
```

Não é preciso instalar o Allure: o plugin do Maven baixa a ferramenta na primeira vez. Para parar o
servidor do relatório, use `Ctrl+C` no terminal. Para só gerar os arquivos, sem abrir, use
`mvn allure:report` (o relatório fica em `target/site/allure-maven-plugin`).

O relatório mostra:

- cada cenário com os passos do Gherkin e o resultado de cada um;
- os cenários agrupados por funcionalidade (uma suíte por feature) e as tags como filtros;
- nos Esquemas do Cenário, os valores da linha de `Exemplos` usada em cada execução;
- informações extras dentro dos passos, como a semente e os dados gerados no formulário;
- quando um cenário falha, um **screenshot** da tela no momento da falha. Se havia um alerta JavaScript
  aberto (o navegador não tira screenshot com alerta aberto), são anexados o texto do alerta e um
  screenshot da página depois que ele foi fechado;
- o ambiente da execução (URL, navegador, modo headless, versão do Java e sistema operacional).

> Use `mvn clean test` antes de gerar o relatório: sem o `clean`, os resultados das execuções
> anteriores continuam na pasta e aparecem misturados no relatório.

### Relatório HTML do Cucumber

Toda execução também gera um relatório HTML do próprio Cucumber, em um único arquivo que pode ser
aberto direto no navegador: `target/cucumber-reports/cucumber.html`.

## Boas práticas adotadas

- **BDD com linguagem de negócio**: as features descrevem o comportamento esperado, sem seletores,
  código ou detalhes técnicos da tela.
- **Gherkin em português**: as features podem ser lidas e revisadas por quem não programa.
- **Reaproveitamento de passos**: `Contexto` para as pré-condições comuns de uma feature,
  `Esquema do Cenário` com `Exemplos` para variações de dados, e `NavegacaoSteps` para os passos
  usados em várias features.
- **Tags**: permitem rodar só uma funcionalidade, só os cenários negativos e assim por diante.
- **Page Object Model**: os steps descrevem o que fazer; os localizadores e as ações ficam nas páginas.
- **Esperas explícitas**: nenhuma sincronização é feita com pausas fixas. O Selenium aguarda o elemento
  ficar visível ou clicável, a janela abrir ou o alerta aparecer.
- **Cenários independentes**: cada cenário abre o próprio navegador, e o Cucumber recria as classes de
  steps a cada cenário, então eles podem rodar em qualquer ordem ou sozinhos.
- **Dados controlados**: os downloads usam arquivos fixos do site, e os dados aleatórios podem ser
  reproduzidos com a semente.
- **Evidência de falha**: screenshot anexado automaticamente ao relatório quando um cenário falha.

## Como criar um novo cenário

1. **Escreva o cenário** em um arquivo `.feature` em `src/test/resources/features`, começando com
   `# language: pt` e com uma tag para a funcionalidade. Reaproveite os passos que já existem (como
   `Dado que estou na página inicial`) sempre que possível.
2. **Rode só esse cenário** (por exemplo, pela tag). O Cucumber avisa quais passos ainda não têm step
   definition e mostra no terminal um esqueleto do método para cada um deles, pronto para copiar.
3. **Crie o page object**, se a página ainda não tiver um, em `src/main/java/pages`, herdando de
   `BasePage`. Se ela for acessada pela página inicial, adicione o link na `HomePage` e no
   `NavegacaoSteps`.
4. **Crie a classe de steps** em `src/test/java/steps`, cole os métodos gerados e implemente cada um
   usando os page objects. Obtenha o navegador com `Hooks.getDriver()` e use as asserções do JUnit
   (`assertEquals`, `assertTrue`...) nos passos `Então`.
5. Cada frase de passo precisa ser **única** em todo o projeto: duas step definitions com o mesmo
   texto fazem o Cucumber parar com erro de passo ambíguo.

## Solução de problemas

| Problema | Causa provável | Como resolver |
|---|---|---|
| `mvn: command not found` / `'mvn' não é reconhecido` | Maven não instalado ou fora do `PATH` | Instale o Maven e adicione a pasta `bin` ao `PATH`, ou rode pelo IntelliJ |
| `No compiler is provided in this environment` | O `JAVA_HOME` aponta para um JRE | Instale o JDK 21 e aponte o `JAVA_HOME` para ele. Confira com `javac -version` |
| `there is no POM in this directory` | Comando executado fora da pasta do projeto | Entre na pasta `selenium-java-cucumber` antes de rodar o `mvn` |
| Todos os cenários aparecem como `Skipped` | O filtro não encontrou nenhum cenário | Confira a tag; no filtro por nome, use `.*` no começo e no fim da expressão |
| Opção `-D...` ignorada no PowerShell | O PowerShell quebrou o argumento no ponto | Coloque a opção inteira entre aspas: `"-Dcucumber.filter.tags=@login"` |
| `Undefined step` / passo sem definição | O texto do passo na feature não bate com nenhuma step definition | Confira o texto (inclusive acentos e aspas) ou crie o método com o esqueleto mostrado no terminal |
| `WebDriver nao iniciado` | Os hooks não foram carregados (comum ao rodar o `.feature` pelo IntelliJ) | Preencha o **Glue** da configuração do Cucumber com `hooks steps`, ou rode pelo runner |
| Aviso `Unable to find an exact match for CDP version ...` | O Chrome é mais novo que o suporte a DevTools do Selenium | É só um aviso e não afeta os testes; some quando o Selenium for atualizado |
| Erro ao iniciar o Chrome / `SessionNotCreatedException` | Chrome não instalado, ou sem internet para baixar o driver | Instale ou atualize o Google Chrome e confira a conexão na primeira execução |
| Timeout esperando um elemento | Site lento ou fora do ar | Abra https://the-internet.herokuapp.com no navegador e rode de novo |

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](../LICENSE).
