package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import utils.TestLogger;

/**
 * Plugin do Cucumber que faz a pausa de visualização depois de cada passo do Gherkin, para dar
 * tempo de ver a ação no navegador (nenhuma pausa no modo headless; veja
 * {@code ConfigReader.getPausaEntrePassos()}).
 * <p>
 * Fica em um plugin, e não em um hook {@code @AfterStep}, porque os hooks aparecem como passos
 * no relatório do Allure: um {@code @AfterStep} geraria uma linha extra depois de cada passo real.
 * Um plugin só escuta os eventos da execução e não aparece no relatório.
 * <p>
 * Registrado no runner ({@code TodosOsCenariosCucumberTest}), na lista de plugins.
 */
public class PausaEntrePassosPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepFinished.class, this::pausarDepoisDoPasso);
    }

    /**
     * Pausa só depois dos passos do Gherkin que foram executados: hooks e passos pulados (os que
     * vêm depois de uma falha) não têm nada para mostrar na tela.
     */
    private void pausarDepoisDoPasso(TestStepFinished evento) {
        if (evento.getTestStep() instanceof PickleStepTestStep
                && evento.getResult().getStatus() != Status.SKIPPED) {
            TestLogger.pausarEntrePassos();
        }
    }
}
