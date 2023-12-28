package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.service.DetteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class DetteItemProcessorTest {

    @InjectMocks
    private DetteItemProcessor detteItemProcessor;

    @Mock
    private DetteService detteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessDetteAboveThreshold() throws Exception {
        Dette dette = createDette(2500.00);

        when(detteService.saveDette(any(Dette.class))).thenReturn(dette);

        Dette processedDette = detteItemProcessor.process(dette);

        assertThat(processedDette).isEqualTo(dette);
    }

    @Test
    public void testProcessDetteBelowThreshold() throws Exception {
        Dette dette = createDette(1500.00);

        when(detteService.saveDette(any(Dette.class))).thenReturn(dette);

        Dette processedDette = detteItemProcessor.process(dette);

        assertThat(processedDette).isEqualTo(dette);
    }

    @Test
    public void testProcessDetteWithZeroAmount() throws Exception {
        Dette dette = createDette(0.00);

        when(detteService.saveDette(any(Dette.class))).thenReturn(dette);

        Dette processedDette = detteItemProcessor.process(dette);

        assertThat(processedDette).isEqualTo(dette);
    }

    @Test
    public void testProcessDetteWithNegativeAmount() throws Exception {
        Dette dette = createDette(-100.00);

        when(detteService.saveDette(any(Dette.class))).thenReturn(dette);

        Dette processedDette = detteItemProcessor.process(dette);

        assertThat(processedDette).isEqualTo(dette);
    }

    private Dette createDette(double amount) {
        Dette dette = new Dette();
        Entreprise entreprise = new Entreprise();
        entreprise.setId(123L);  // Supposons un ID pour l'entreprise
        dette.setEntreprise(entreprise);
        dette.setAmount(amount);
        return dette;
    }
}
