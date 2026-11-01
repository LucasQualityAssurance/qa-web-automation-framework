package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Validates that the automation always opens https://the-internet.herokuapp.com/
 * as a pre-requisite, confirming the "Welcome to the-internet" message is displayed.
 */
public class HomePageTest extends BaseTest {

    @Test
    @DisplayName("Deve abrir a home page e exibir a mensagem de boas-vindas")
    public void deveAbrirHomePageEExibirMensagemDeBoasVindas() {
        assertEquals("Welcome to the-internet", homePage.getHeadingText());
    }
}
