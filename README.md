# qa-web-automation-framework

[![testes](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/testes.yml/badge.svg)](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/testes.yml)
[![Relatório Allure](https://img.shields.io/badge/relatório-online-2f6fde)](https://lucasqualityassurance.github.io/qa-web-automation-framework/)
![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4-43B02A)
![Allure](https://img.shields.io/badge/Allure-Report-yellow)
![License](https://img.shields.io/badge/license-MIT-blue)

Portfólio de automação de testes Web em **Java + Selenium**, com duas abordagens para o mesmo
sistema: testes escritos **100% em Java** com JUnit 5 e testes **BDD com Cucumber**. Os dois
projetos cobrem os mesmos fluxos, geram relatório no **Allure** e rodam automaticamente no
**GitHub Actions**.

📊 **[Ver os relatórios da última execução](https://lucasqualityassurance.github.io/qa-web-automation-framework/)** (Allure e Cucumber, publicados no GitHub Pages).

## Sistema testado

Os testes automatizam o [**The Internet**](https://the-internet.herokuapp.com), um site **open
source** mantido pela Sauce Labs para praticar automação
([repositório no GitHub](https://github.com/saucelabs/the-internet)). Ele reúne situações comuns do
dia a dia de QA em páginas independentes: login, formulários, download e upload de arquivos, alertas
JavaScript, múltiplas janelas, carregamento dinâmico de elementos e outras.

## Projetos

| Pasta | Abordagem | Status |
|---|---|---|
| [`core/`](core) | Código compartilhado: page objects, `DriverFactory`, utilitários e massa de dados | ✅ Pronto |
| [`selenium-java/`](selenium-java) | Java puro: JUnit 5 + Page Object Model | ✅ Pronto |
| [`selenium-java-cucumber/`](selenium-java-cucumber) | BDD: Cucumber + Gherkin em português + Page Object Model | ✅ Pronto |

O repositório é um projeto **Maven multi-módulo**. As duas abordagens de teste usam o **mesmo**
módulo `core`: uma correção em um page object vale para os dois projetos, sem código duplicado. O
`pom.xml` da raiz centraliza as versões das dependências e dos plugins.

### selenium-java: Java 100%

Testes escritos em Java com **JUnit 5**, seguindo o padrão **Page Object Model**: cada página do
site tem uma classe com seus localizadores e ações, e os testes só conversam com essas classes.

- 7 classes de teste e 14 execuções: login válido e inválido, formulário com dados aleatórios
  reproduzíveis, download e upload, carregamento dinâmico, alertas e múltiplas janelas.
- Esperas explícitas, testes independentes e testes parametrizados (`@ParameterizedTest`).
- Modo headless e pausa configurável entre os passos para acompanhar a execução na tela.

👉 Detalhes, cenários e passo a passo em [`selenium-java/README.md`](selenium-java/README.md).

### selenium-java-cucumber: BDD

Os mesmos fluxos escritos como cenários em **Gherkin** (`Dado / Quando / Então`), em português,
ligados a step definitions em Java e executados pelo **Cucumber** na JUnit Platform. Os page
objects são os mesmos do `selenium-java`, vindos do módulo `core`.

- 6 features e 14 cenários: login válido e inválido, alertas JavaScript, formulário com dados
  aleatórios reproduzíveis, carregamento dinâmico, download e upload e múltiplas janelas.
- `Contexto`, `Esquema do Cenário` com `Exemplos` e tags para rodar só uma parte dos cenários
  (ex.: `mvn test "-Dcucumber.filter.tags=@login and @negativo"`).
- Hooks que abrem um navegador por cenário e anexam um screenshot ao relatório quando o cenário falha.

👉 Detalhes, cenários e passo a passo em [`selenium-java-cucumber/README.md`](selenium-java-cucumber/README.md).

## Estrutura do repositório

```
qa-web-automation-framework/
├── .github/
│   ├── workflows/testes.yml     # CI: roda os dois projetos e publica os relatórios no GitHub Pages
│   └── pages/index.html         # Página inicial dos relatórios publicados
├── pom.xml                      # POM pai: módulos, versões e plugins compartilhados
├── core/                        # Page objects, DriverFactory, utilitários e massa de dados
├── selenium-java/               # Testes JUnit 5
├── selenium-java-cucumber/      # Cenários BDD com Cucumber
├── Files/                       # Downloads feitos pelos testes (criada na execução, ignorada pelo Git)
├── LICENSE
└── README.md
```

## Tecnologias

| Ferramenta | Uso |
|---|---|
| Java 21 | Linguagem dos testes |
| Selenium WebDriver 4 | Controle do navegador; o ChromeDriver é baixado automaticamente pelo Selenium Manager |
| JUnit 5 | Execução dos testes do `selenium-java` |
| Cucumber 7 | Cenários BDD do `selenium-java-cucumber` |
| Maven | Build, dependências e execução pela linha de comando |
| Allure Report | Relatório com os passos de cada teste |
| GitHub Actions | Integração contínua |

## Relatórios

Os relatórios da última execução na `main` ficam publicados no **GitHub Pages**:
**https://lucasqualityassurance.github.io/qa-web-automation-framework/**

| Relatório | Conteúdo |
|---|---|
| Allure: `selenium-java` | Cada teste com seus passos, o resultado e o ambiente da execução |
| Allure: `selenium-java-cucumber` | Passos do Gherkin, cenários agrupados por funcionalidade e screenshot nas falhas |
| HTML do Cucumber | Relatório nativo do Cucumber, em um único arquivo |

Para gerar o relatório Allure na sua máquina, na raiz do repositório:

```bash
mvn clean test -pl selenium-java -am
mvn allure:serve -pl selenium-java
```

Não é preciso instalar o Allure: o plugin do Maven baixa a ferramenta na primeira execução.

## Integração contínua (GitHub Actions)

O workflow [`testes.yml`](.github/workflows/testes.yml) roda a cada push ou pull request na `main`
e também pode ser disparado manualmente na aba **Actions**. Ele:

1. roda os dois projetos **em paralelo** (um job para cada), no Chrome em modo headless, com JDK 21 e cache do Maven;
2. gera os relatórios Allure, mesmo quando algum teste falha;
3. publica os relatórios como artefatos da execução, disponíveis por 14 dias;
4. na `main`, publica os relatórios no **GitHub Pages**.

No disparo manual dá para informar uma expressão de tags do Cucumber (ex.: `@login and @negativo`)
para rodar só parte dos cenários.

## Como rodar localmente

Pré-requisitos: **JDK 21**, **Maven 3.9+** e **Google Chrome**.

```bash
git clone https://github.com/LucasQualityAssurance/qa-web-automation-framework.git
cd qa-web-automation-framework
mvn test                                         # roda os dois projetos
mvn test -pl selenium-java -am                   # só o projeto JUnit 5
mvn test -pl selenium-java-cucumber -am          # só o projeto Cucumber
mvn test -Dheadless=true                         # sem abrir janela
```

Os comandos rodam na **raiz** do repositório: `-pl` escolhe o módulo e `-am` compila junto o `core`.
No `selenium-java-cucumber`, dá para rodar só uma funcionalidade pela tag:

```bash
mvn test -pl selenium-java-cucumber -am "-Dcucumber.filter.tags=@alertas"
```

O passo a passo completo (instalação no Windows, macOS e Linux, IntelliJ e solução de problemas)
está no README de cada projeto.

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).
