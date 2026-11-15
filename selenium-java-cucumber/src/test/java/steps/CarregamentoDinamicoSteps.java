package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.DynamicLoadingExamplePage;
import pages.DynamicLoadingPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Steps da funcionalidade de carregamento dinâmico ({@code features/carregamento_dinamico.feature}).
 * A sincronização é feita só com esperas explícitas, sem pausas fixas.
 */
public class CarregamentoDinamicoSteps {

    private DynamicLoadingExamplePage exemploPage;

    @Quando("abro o exemplo {int} de carregamento dinâmico")
    public void abroOExemploDeCarregamentoDinamico(int exemplo) {
        exemploPage = new DynamicLoadingPage(Hooks.getDriver()).acessarExemplo(exemplo);
    }

    @Entao("a mensagem final não deve estar visível")
    public void aMensagemFinalNaoDeveEstarVisivel() {
        assertFalse(exemploPage.mensagemFinalEstaVisivel(),
                "A mensagem final nao deveria estar visivel antes do clique em 'Start'");
    }

    @Quando("inicio o carregamento")
    public void inicioOCarregamento() {
        exemploPage.iniciar();
    }

    @Entao("devo ver a mensagem final {string}")
    public void devoVerAMensagemFinal(String mensagemEsperada) {
        assertEquals(mensagemEsperada, exemploPage.obterMensagemFinal());
    }
}
