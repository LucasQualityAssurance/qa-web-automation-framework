package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FormResultPage;
import pages.LargeFormPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Validates the form submission flow at https://the-internet.herokuapp.com/large_form.
 * Starting from the home page, the user opens the Large Form example and fills in every
 * field on the screen (account data, details, multi-select languages, contact preference
 * radio, interests checkboxes, experience slider and favorite color picker), submits it,
 * and confirms the "Form Submitted" confirmation heading.
 */
public class CadastroFormularioTest extends BaseTest {

    @Test
    @DisplayName("Deve preencher e enviar o formulario grande com sucesso")
    public void devePreencherEEnviarFormularioComSucesso() {
        TestLogger.step("Abrindo a home page (/)");
        homePage.abrir();

        TestLogger.step("Clicando no link 'Large Form'");
        LargeFormPage largeFormPage = homePage.acessarFormularioGrande();

        TestLogger.step("Preenchendo dados da conta (nome, sobrenome, email, senha, apelido)");
        TestLogger.step("Preenchendo dados de detalhes (aniversario, idade, pais, sobre voce)");
        largeFormPage.preencherFormulario(
                "Lucas",
                "Silva",
                "lucas.silva@example.com",
                "SenhaForte123",
                "lucas.qa",
                "1995-05-20",
                "30",
                "us",
                "QA Automation Engineer");

        TestLogger.step("Selecionando idiomas (English, Spanish)");
        largeFormPage.selecionarIdiomas("English", "Spanish");

        TestLogger.step("Selecionando preferencia de contato (telefone)");
        largeFormPage.selecionarContato("phone");

        TestLogger.step("Selecionando interesses (testing, automation, devops)");
        largeFormPage.selecionarInteresses("testing", "automation", "devops");

        TestLogger.step("Definindo anos de experiencia (10)");
        largeFormPage.definirAnosDeExperiencia(10);

        TestLogger.step("Definindo cor favorita (#00ff00)");
        largeFormPage.definirCorFavorita("#00ff00");

        TestLogger.step("Aceitando os termos");
        largeFormPage.aceitarTermos();

        TestLogger.step("Enviando o formulario");
        FormResultPage formResultPage = largeFormPage.enviar();

        TestLogger.step("Validando mensagem de confirmacao");
        assertEquals("Form Submitted", formResultPage.obterTitulo());

        TestLogger.step("Formulario enviado com sucesso");
    }
}
