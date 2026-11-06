# qa-web-automation-framework

[![selenium-java](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/selenium-java.yml/badge.svg)](https://github.com/LucasQualityAssurance/qa-web-automation-framework/actions/workflows/selenium-java.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4-43B02A)
![Allure](https://img.shields.io/badge/Allure-Report-yellow)
![License](https://img.shields.io/badge/license-MIT-blue)

Portfólio de automação de testes Web em **Java + Selenium**, com duas abordagens para o mesmo
sistema: testes escritos **100% em Java** com JUnit 5 e testes **BDD com Cucumber**. Os dois
projetos geram relatório no **Allure**, e a execução roda automaticamente no **GitHub Actions**.

## Sistema testado

Os testes automatizam o [**The Internet**](https://the-internet.herokuapp.com), um site **open
source** mantido pela Sauce Labs para praticar automação
([repositório no GitHub](https://github.com/saucelabs/the-internet)). Ele reúne situações comuns do
dia a dia de QA em páginas independentes: login, formulários, download e upload de arquivos, alertas
JavaScript, múltiplas janelas, carregamento dinâmico de elementos e outras.

## Projetos

| Pasta | Abordagem | Status |
|---|---|---|
| [`selenium-java/`](selenium-java) | Java puro: JUnit 5 + Page Object Model | ✅ Pronto |
| [`selenium-java-cucumber/`](selenium-java-cucumber) | BDD: Cucumber + Gherkin em português + Page Object Model | 🚧 Em desenvolvimento |

Cada pasta é um projeto Maven independente, com o próprio `pom.xml` e o próprio README explicando
como rodar.

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
ligados a step definitions em Java e executados pelo **Cucumber** na JUnit Platform. A base do
projeto já está pronta; os cenários serão adicionados nas próximas etapas.

👉 Veja [`selenium-java-cucumber/README.md`](selenium-java-cucumber/README.md).

## Estrutura do repositório

```
qa-web-automation-framework/
├── .github/workflows/
│   └── selenium-java.yml        # Pipeline de CI no GitHub Actions
├── selenium-java/               # Projeto JUnit 5 + Page Object Model
├── selenium-java-cucumber/      # Projeto BDD com Cucumber
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

## Relatório Allure

Os dois projetos gravam os resultados em `target/allure-results`. Para gerar e abrir o relatório,
dentro da pasta do projeto:

```bash
mvn clean test
mvn allure:serve
```

Não é preciso instalar o Allure: o plugin do Maven baixa a ferramenta na primeira execução. O
relatório mostra cada teste com seus passos, o resultado e o ambiente da execução (navegador, modo
headless, versão do Java e sistema operacional).

## Integração contínua (GitHub Actions)

O workflow [`selenium-java.yml`](.github/workflows/selenium-java.yml) roda a cada push ou pull
request na `main` que altere o projeto `selenium-java`, e também pode ser disparado manualmente na
aba **Actions**. Ele:

1. instala o JDK 21 com cache das dependências do Maven;
2. roda todos os testes no Chrome em modo headless;
3. gera o relatório Allure, mesmo quando algum teste falha;
4. publica o relatório como artefato `allure-report` da execução, disponível por 14 dias.

Para ver o relatório, abra a execução na aba **Actions**, baixe o artefato `allure-report` e abra o
`index.html` com um servidor local (por exemplo, `npx http-server` dentro da pasta extraída).

## Como rodar localmente

Pré-requisitos: **JDK 21**, **Maven 3.9+** e **Google Chrome**.

```bash
git clone https://github.com/LucasQualityAssurance/qa-web-automation-framework.git
cd qa-web-automation-framework/selenium-java   # ou selenium-java-cucumber
mvn test                                       # com o navegador visível
mvn test -Dheadless=true                       # sem abrir janela
```

O passo a passo completo (instalação no Windows, macOS e Linux, IntelliJ e solução de problemas)
está no README de cada projeto.

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).
