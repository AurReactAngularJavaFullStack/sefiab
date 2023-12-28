package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.service.EntrepriseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class EntrepriseItemProcessorTest {

    @InjectMocks
    private EntrepriseItemProcessor processor;

    @Mock
    private EntrepriseService entrepriseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() throws Exception {
        Entreprise entreprise = new Entreprise();
        // TODO: Set any required fields on the `entreprise` object

        Entreprise savedEntreprise = new Entreprise();
        // TODO: Set any required fields on the `savedEntreprise` object

        when(entrepriseService.saveEntreprise(any(Entreprise.class))).thenReturn(savedEntreprise);

        Entreprise result = processor.process(entreprise);
        assertThat(result).isEqualTo(savedEntreprise);
    }
}
