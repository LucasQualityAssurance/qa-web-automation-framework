# core

> Parte do repositório [qa-web-automation-framework](../README.md).

Módulo com o código compartilhado pelos dois projetos de teste,
[`selenium-java`](../selenium-java) e [`selenium-java-cucumber`](../selenium-java-cucumber). Ele não
tem testes: os dois projetos dependem dele no `pom.xml`, e o Maven compila o `core` junto quando o
comando usa `-am`.

| Pacote | Conteúdo |
|---|---|
| `pages/` | Page objects: `BasePage`, com as interações comuns e esperas explícitas, e uma classe por página do site |
| `factory/` | `DriverFactory`: cria o Chrome (pasta de downloads, modo headless) |
| `utils/` | `ConfigReader`, `TestLogger`, `ArquivoUtils` e `AllureAmbiente` |
| `dados/` | Massa de teste: credenciais e gerador de dados aleatórios reproduzíveis |

Uma mudança aqui vale para os dois projetos. Por isso, depois de alterar o `core`, rode os testes
dos dois:

```bash
mvn test
```
