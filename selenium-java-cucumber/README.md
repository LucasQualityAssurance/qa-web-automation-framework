# selenium-java-cucumber

> 🚧 **Em desenvolvimento.** Esta pasta contém a base do projeto (dependências e estrutura). Os
> cenários e os step definitions serão adicionados nas próximas etapas.

Versão BDD da automação do [The Internet](https://the-internet.herokuapp.com): os mesmos fluxos do
projeto [`selenium-java`](../selenium-java), escritos como cenários em **Gherkin** (português) e
executados pelo **Cucumber**.

## Tecnologias

| Ferramenta | Versão | Para que serve |
|---|---|---|
| Java (JDK) | 21 | Linguagem dos testes |
| Selenium WebDriver | 4.38.0 | Controle do navegador |
| Cucumber | 7.20.1 | Cenários em Gherkin (`.feature`) ligados a step definitions em Java |
| JUnit Platform Suite | 1.13.4 | Executa o Cucumber pelo Maven/Surefire e pelo IntelliJ |
| Allure Report | 2.35.1 | Relatório com cada cenário e seus passos |

## Estrutura planejada

```
selenium-java-cucumber/
├── pom.xml
└── src
    ├── main/java
    │   ├── factory/          # Criação do Chrome
    │   ├── pages/            # Page objects
    │   └── utils/            # Configurações e utilitários
    └── test
        ├── java
        │   ├── hooks/        # Abre e fecha o navegador em cada cenário (@Before / @After)
        │   ├── runner/       # Classe que dispara o Cucumber pela JUnit Platform
        │   └── steps/        # Step definitions (Dado / Quando / Então)
        └── resources
            └── features/     # Cenários em Gherkin, em português (# language: pt)
```

## Como rodar

Dentro da pasta `selenium-java-cucumber`:

```bash
mvn test                      # com o navegador visível
mvn test -Dheadless=true      # sem abrir janela
mvn allure:serve              # abre o relatório Allure
```

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](../LICENSE).
