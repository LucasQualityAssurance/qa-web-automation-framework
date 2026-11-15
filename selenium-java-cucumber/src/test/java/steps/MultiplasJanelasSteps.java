package steps;

import hooks.Hooks;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.MultipleWindowsPage;
import pages.NewWindowPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Steps da funcionalidade de múltiplas janelas ({@code features/multiplas_janelas.feature}).
 */
public class MultiplasJanelasSteps {

    private NewWindowPage novaJanela;
    private MultipleWindowsPage janelaOriginal;

    @Quando("clico no link que abre uma nova janela")
    public void clicoNoLinkQueAbreUmaNovaJanela() {
        novaJanela = new MultipleWindowsPage(Hooks.getDriver()).abrirNovaJanela();
    }

    @Entao("devo ter {int} janela(s) aberta(s)")
    public void devoTerJanelasAbertas(int quantidadeEsperada) {
        assertEquals(quantidadeEsperada, new MultipleWindowsPage(Hooks.getDriver()).obterQuantidadeDeJanelas());
    }

    @Entao("a nova janela deve exibir o título {string} na aba e na página")
    public void aNovaJanelaDeveExibirOTitulo(String tituloEsperado) {
        assertEquals(tituloEsperado, novaJanela.obterTituloDaAba());
        assertEquals(tituloEsperado, novaJanela.obterTitulo());
    }

    @Quando("fecho a nova janela e volto para a original")
    public void fechoANovaJanelaEVoltoParaAOriginal() {
        janelaOriginal = novaJanela.fecharEVoltarParaJanelaOriginal();
    }

    @Entao("a janela original deve exibir o título {string}")
    public void aJanelaOriginalDeveExibirOTitulo(String tituloEsperado) {
        assertEquals(tituloEsperado, janelaOriginal.obterTitulo());
    }
}
