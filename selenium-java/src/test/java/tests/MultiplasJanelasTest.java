package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MultipleWindowsPage;
import pages.NewWindowPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Valida a troca de janelas em https://the-internet.herokuapp.com/windows: abre a página
 * "New Window" em uma nova aba, troca o foco para ela e confere o conteúdo, depois fecha a
 * aba e confirma que o foco voltou para a janela original.
 */
public class MultiplasJanelasTest extends BaseTest {

    @Test
    @DisplayName("Deve abrir uma nova janela e voltar para a original")
    public void deveAbrirNovaJanelaEVoltarParaOriginal() {
        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'Multiple Windows'");
        MultipleWindowsPage windowsPage = homePage.acessarMultiplasJanelas();

        TestLogger.step("Clicando em 'Click Here' e trocando para a nova aba");
        NewWindowPage novaJanela = windowsPage.abrirNovaJanela();

        TestLogger.step("Validando que existem 2 janelas abertas");
        assertEquals(2, windowsPage.obterQuantidadeDeJanelas());

        TestLogger.step("Validando o conteudo da nova aba");
        assertEquals("New Window", novaJanela.obterTituloDaAba());
        assertEquals("New Window", novaJanela.obterTitulo());

        TestLogger.step("Fechando a nova aba e voltando para a janela original");
        MultipleWindowsPage janelaOriginal = novaJanela.fecharEVoltarParaJanelaOriginal();

        TestLogger.step("Validando que resta 1 janela aberta e que o foco voltou para ela");
        assertEquals(1, janelaOriginal.obterQuantidadeDeJanelas());
        assertEquals("Opening a new window", janelaOriginal.obterTitulo());

        TestLogger.step("Troca de janelas concluida com sucesso");
    }
}
