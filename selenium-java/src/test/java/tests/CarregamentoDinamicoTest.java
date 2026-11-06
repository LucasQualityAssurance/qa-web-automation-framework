package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.DynamicLoadingExamplePage;
import pages.DynamicLoadingPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Valida os dois exemplos de carregamento dinâmico em
 * https://the-internet.herokuapp.com/dynamic_loading. Em cada um, confirma que a mensagem
 * "Hello World!" não aparece antes do clique em "Start" e que aparece depois do carregamento,
 * usando apenas espera explícita (sem pausas fixas). Cada linha da tabela é um exemplo.
 */
public class CarregamentoDinamicoTest extends BaseTest {

    @ParameterizedTest(name = "{1}")
    @DisplayName("Deve exibir a mensagem depois do carregamento dinamico")
    @CsvSource({
            "1, Exemplo 1: elemento ja existe na pagina oculto",
            "2, Exemplo 2: elemento criado depois do carregamento"
    })
    public void deveExibirMensagemDepoisDoCarregamento(int exemplo, String cenario) {
        TestLogger.info("Cenario: " + cenario);

        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'Dynamic Loading'");
        DynamicLoadingPage dynamicLoadingPage = homePage.acessarCarregamentoDinamico();

        TestLogger.step("Abrindo o exemplo " + exemplo);
        DynamicLoadingExamplePage exemploPage = dynamicLoadingPage.acessarExemplo(exemplo);

        TestLogger.step("Validando que a mensagem nao esta visivel antes de iniciar");
        assertFalse(exemploPage.mensagemFinalEstaVisivel(),
                "A mensagem final nao deveria estar visivel antes do clique em 'Start'");

        TestLogger.step("Clicando em 'Start' e aguardando o fim do carregamento");
        exemploPage.iniciar();

        TestLogger.step("Validando a mensagem exibida");
        assertEquals("Hello World!", exemploPage.obterMensagemFinal());

        TestLogger.step("Carregamento dinamico concluido com sucesso");
    }
}
