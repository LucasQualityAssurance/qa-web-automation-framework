package steps;

import dados.DadosFormulario;
import hooks.Hooks;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.FormResultPage;
import pages.LargeFormPage;
import utils.TestLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Steps da funcionalidade de cadastro no Large Form ({@code features/cadastro_formulario.feature}).
 * Os dados vêm de {@link DadosFormulario#aleatorios()}; a semente é registrada para que uma
 * falha possa ser repetida com {@code -Dseed=<valor>}.
 */
public class CadastroFormularioSteps {

    private DadosFormulario dados;
    private FormResultPage formResultPage;

    private LargeFormPage largeFormPage() {
        return new LargeFormPage(Hooks.getDriver());
    }

    @Dado("que gerei dados aleatórios para o formulário")
    public void queGereiDadosAleatoriosParaOFormulario() {
        dados = DadosFormulario.aleatorios();
        TestLogger.info("Dados aleatorios gerados (para repetir: -Dseed=" + dados.semente() + ")");
    }

    @Quando("preencho os dados da conta e os detalhes")
    public void preenchoOsDadosDaContaEOsDetalhes() {
        TestLogger.info("Conta: " + dados.primeiroNome() + " " + dados.sobrenome()
                + ", " + dados.email() + ", apelido " + dados.apelido());
        TestLogger.info("Detalhes: aniversario " + dados.aniversario() + ", idade " + dados.idade()
                + ", pais " + dados.pais() + ", " + dados.sobreVoce());
        largeFormPage().preencherFormulario(
                dados.primeiroNome(),
                dados.sobrenome(),
                dados.email(),
                dados.senha(),
                dados.apelido(),
                dados.aniversario(),
                dados.idade(),
                dados.pais(),
                dados.sobreVoce());
    }

    @Quando("seleciono os idiomas, a preferência de contato e os interesses")
    public void selecionoOsIdiomasAPreferenciaDeContatoEOsInteresses() {
        TestLogger.info("Idiomas " + dados.idiomas() + ", contato " + dados.contato()
                + ", interesses " + dados.interesses());
        largeFormPage()
                .selecionarIdiomas(dados.idiomas().toArray(String[]::new))
                .selecionarContato(dados.contato())
                .selecionarInteresses(dados.interesses().toArray(String[]::new));
    }

    @Quando("defino os anos de experiência e a cor favorita")
    public void definoOsAnosDeExperienciaEACorFavorita() {
        TestLogger.info("Anos de experiencia " + dados.anosDeExperiencia() + ", cor favorita " + dados.corFavorita());
        largeFormPage()
                .definirAnosDeExperiencia(dados.anosDeExperiencia())
                .definirCorFavorita(dados.corFavorita());
    }

    @Quando("aceito os termos")
    public void aceitoOsTermos() {
        largeFormPage().aceitarTermos();
    }

    @Quando("envio o formulário")
    public void envioOFormulario() {
        formResultPage = largeFormPage().enviar();
    }

    @Entao("devo ver a confirmação de envio {string}")
    public void devoVerAConfirmacaoDeEnvio(String tituloEsperado) {
        assertEquals(tituloEsperado, formResultPage.obterTitulo());
    }
}
