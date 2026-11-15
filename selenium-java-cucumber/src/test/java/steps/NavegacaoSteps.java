package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

/**
 * Steps de navegação compartilhados por todas as features: abrir a página inicial e entrar em
 * um dos exemplos listados nela. Cada feature continua a partir da página aberta aqui, usando
 * os próprios steps.
 */
public class NavegacaoSteps {

    @Dado("que estou na página inicial")
    public void queEstouNaPaginaInicial() {
        new HomePage(Hooks.getDriver()).abrir();
    }

    @Quando("volto para a página inicial")
    public void voltoParaAPaginaInicial() {
        new HomePage(Hooks.getDriver()).abrir();
    }

    /**
     * Clica no link de um exemplo da página inicial.
     *
     * @param exemplo nome do exemplo como aparece na página (ex.: "JavaScript Alerts").
     */
    @Quando("acesso o exemplo {string}")
    public void acessoOExemplo(String exemplo) {
        HomePage homePage = new HomePage(Hooks.getDriver());
        switch (exemplo) {
            case "Large Form" -> homePage.acessarFormularioGrande();
            case "File Download" -> homePage.acessarDownloadDeArquivos();
            case "File Upload" -> homePage.acessarUploadDeArquivos();
            case "Dynamic Loading" -> homePage.acessarCarregamentoDinamico();
            case "JavaScript Alerts" -> homePage.acessarAlertasJavaScript();
            case "Multiple Windows" -> homePage.acessarMultiplasJanelas();
            default -> throw new IllegalArgumentException("Exemplo desconhecido na página inicial: " + exemplo);
        }
    }
}
