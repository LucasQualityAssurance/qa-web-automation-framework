package dados;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Dados usados para preencher o Large Form (https://the-internet.herokuapp.com/large_form).
 * <p>
 * {@link #aleatorios()} gera um conjunto aleatório novo a cada execução, sempre dentro dos valores
 * que o formulário aceita (país, idiomas, contato, interesses, experiência de 0 a 30). Todos os
 * valores vêm de uma única semente, guardada em {@link #semente()}: quando uma execução falha, ela
 * pode ser repetida com exatamente os mesmos dados passando {@code -Dseed=<valor>} ao Maven.
 */
public record DadosFormulario(
        long semente,
        String primeiroNome,
        String sobrenome,
        String email,
        String senha,
        String apelido,
        String aniversario,
        String idade,
        String pais,
        String sobreVoce,
        List<String> idiomas,
        String contato,
        List<String> interesses,
        int anosDeExperiencia,
        String corFavorita) {

    private static final List<String> NOMES = List.of(
            "Lucas", "Ana", "Bruno", "Carla", "Diego", "Fernanda", "Gabriel", "Julia", "Marcos", "Paula");
    private static final List<String> SOBRENOMES = List.of(
            "Silva", "Souza", "Oliveira", "Santos", "Pereira", "Costa", "Almeida", "Ribeiro", "Lima", "Gomes");
    private static final List<String> PROFISSOES = List.of(
            "QA Automation Engineer", "Software Developer", "DevOps Engineer", "Product Owner", "Test Analyst");

    /** Valores das opções do select "Country". */
    private static final List<String> PAISES = List.of("ar", "de", "in", "us");
    /** Texto visível das opções da seleção múltipla "Languages". */
    private static final List<String> IDIOMAS = List.of("English", "German", "Hindi", "Spanish");
    /** Valores dos radio buttons "Contact me by". */
    private static final List<String> CONTATOS = List.of("email", "phone", "none");
    /** Valores dos checkboxes "Interests". */
    private static final List<String> INTERESSES = List.of("testing", "automation", "devops");

    private static final int EXPERIENCIA_MAXIMA = 30;
    private static final String CARACTERES_SENHA =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";

    /** Cópias defensivas, para que as listas de um conjunto gerado não possam ser alteradas depois. */
    public DadosFormulario {
        idiomas = List.copyOf(idiomas);
        interesses = List.copyOf(interesses);
    }

    /**
     * Gera um conjunto de dados aleatório. A semente vem da propriedade de sistema {@code seed}
     * quando ela é informada (para reproduzir uma execução anterior) ou, caso contrário, do horário atual.
     *
     * @return um novo conjunto de dados aleatório, válido para o Large Form.
     */
    public static DadosFormulario aleatorios() {
        return aleatorios(Long.getLong("seed", System.nanoTime()));
    }

    /**
     * Gera o conjunto de dados derivado da semente informada: a mesma semente sempre gera os mesmos dados.
     *
     * @param semente semente do gerador aleatório.
     * @return o conjunto de dados dessa semente, válido para o Large Form.
     */
    public static DadosFormulario aleatorios(long semente) {
        Random random = new Random(semente);

        String primeiroNome = sortear(random, NOMES);
        String sobrenome = sortear(random, SOBRENOMES);
        String sufixo = String.valueOf(1000 + random.nextInt(9000));

        LocalDate aniversario = LocalDate.now()
                .minusYears(18 + random.nextInt(48))
                .minusDays(random.nextInt(365));
        int idade = Period.between(aniversario, LocalDate.now()).getYears();

        return new DadosFormulario(
                semente,
                primeiroNome,
                sobrenome,
                (primeiroNome + "." + sobrenome + sufixo + "@example.com").toLowerCase(),
                gerarSenha(random),
                (primeiroNome + "." + sufixo).toLowerCase(),
                aniversario.format(DateTimeFormatter.ISO_LOCAL_DATE),
                String.valueOf(idade),
                sortear(random, PAISES),
                sortear(random, PROFISSOES),
                sortearAlguns(random, IDIOMAS),
                sortear(random, CONTATOS),
                sortearAlguns(random, INTERESSES),
                random.nextInt(EXPERIENCIA_MAXIMA + 1),
                String.format("#%06x", random.nextInt(0x1000000)));
    }

    private static String sortear(Random random, List<String> opcoes) {
        return opcoes.get(random.nextInt(opcoes.size()));
    }

    /** Sorteia de uma até todas as opções, mantendo a ordem original. */
    private static List<String> sortearAlguns(Random random, List<String> opcoes) {
        List<String> embaralhadas = new ArrayList<>(opcoes);
        Collections.shuffle(embaralhadas, random);
        List<String> escolhidas = new ArrayList<>(embaralhadas.subList(0, 1 + random.nextInt(opcoes.size())));
        escolhidas.sort((a, b) -> opcoes.indexOf(a) - opcoes.indexOf(b));
        return escolhidas;
    }

    /** 12 caracteres com pelo menos uma letra maiúscula, uma minúscula e um número. */
    private static String gerarSenha(Random random) {
        StringBuilder senha = new StringBuilder()
                .append((char) ('A' + random.nextInt(26)))
                .append((char) ('a' + random.nextInt(26)))
                .append(random.nextInt(10));
        while (senha.length() < 12) {
            senha.append(CARACTERES_SENHA.charAt(random.nextInt(CARACTERES_SENHA.length())));
        }
        return senha.toString();
    }
}
