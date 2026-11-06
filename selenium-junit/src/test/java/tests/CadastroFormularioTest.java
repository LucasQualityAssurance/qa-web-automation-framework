package tests;

import base.BaseTest;
import dados.DadosFormulario;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FormResultPage;
import pages.LargeFormPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Valida o fluxo de envio de formulário em https://the-internet.herokuapp.com/large_form.
 * A partir da página inicial, o usuário abre o exemplo Large Form e preenche todos os campos
 * da tela (dados da conta, detalhes, idiomas em seleção múltipla, radio de preferência de
 * contato, checkboxes de interesses, slider de experiência e seletor de cor favorita), envia
 * e confirma o título de confirmação "Form Submitted". Os dados são aleatórios a cada execução
 * (veja {@link DadosFormulario}); a semente vai para o log para que uma falha possa ser repetida.
 */
@Feature("Formulario")
public class CadastroFormularioTest extends BaseTest {

    @Test
    @DisplayName("Deve preencher e enviar o formulario grande com sucesso")
    public void devePreencherEEnviarFormularioComSucesso() {
        DadosFormulario dados = DadosFormulario.aleatorios();
        TestLogger.info("Dados aleatorios gerados (para repetir: -Dseed=" + dados.semente() + ")");

        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'Large Form'");
        LargeFormPage largeFormPage = homePage.acessarFormularioGrande();

        TestLogger.step("Preenchendo dados da conta (" + dados.primeiroNome() + " " + dados.sobrenome()
                + ", " + dados.email() + ", apelido " + dados.apelido() + ")");
        TestLogger.step("Preenchendo dados de detalhes (aniversario " + dados.aniversario()
                + ", idade " + dados.idade() + ", pais " + dados.pais() + ", " + dados.sobreVoce() + ")");
        largeFormPage.preencherFormulario(
                dados.primeiroNome(),
                dados.sobrenome(),
                dados.email(),
                dados.senha(),
                dados.apelido(),
                dados.aniversario(),
                dados.idade(),
                dados.pais(),
                dados.sobreVoce());

        TestLogger.step("Selecionando idiomas " + dados.idiomas());
        largeFormPage.selecionarIdiomas(dados.idiomas().toArray(String[]::new));

        TestLogger.step("Selecionando preferencia de contato (" + dados.contato() + ")");
        largeFormPage.selecionarContato(dados.contato());

        TestLogger.step("Selecionando interesses " + dados.interesses());
        largeFormPage.selecionarInteresses(dados.interesses().toArray(String[]::new));

        TestLogger.step("Definindo anos de experiencia (" + dados.anosDeExperiencia() + ")");
        largeFormPage.definirAnosDeExperiencia(dados.anosDeExperiencia());

        TestLogger.step("Definindo cor favorita (" + dados.corFavorita() + ")");
        largeFormPage.definirCorFavorita(dados.corFavorita());

        TestLogger.step("Aceitando os termos");
        largeFormPage.aceitarTermos();

        TestLogger.step("Enviando o formulario");
        FormResultPage formResultPage = largeFormPage.enviar();

        TestLogger.step("Validando mensagem de confirmacao");
        assertEquals("Form Submitted", formResultPage.obterTitulo());

        TestLogger.step("Formulario enviado com sucesso");
    }
}
