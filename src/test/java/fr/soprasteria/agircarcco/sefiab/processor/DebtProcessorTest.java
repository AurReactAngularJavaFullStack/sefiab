package fr.soprasteria.agircarcco.sefiab.processor;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DebtProcessorTest {

    private DebtProcessor debtProcessor;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        debtProcessor = new DebtProcessor(entrepriseRepository);
    }

    @Test
    public void testProcessDebtForCompany() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("Test Company");

        List<Dette> dettes = new ArrayList<>();
        Dette dette1 = new Dette();
        dette1.setAmount(100.0);
        Dette dette2 = new Dette();
        dette2.setAmount(200.0);
        dettes.add(dette1);
        dettes.add(dette2);

        entreprise.setDettes(dettes);

        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);

        debtProcessor.processDebtForCompany(entreprise);

        assertEquals(300.0, entreprise.getDebtAmount());
        verify(entrepriseRepository, times(1)).save(entreprise);
    }

    @Test
    public void testProcessDebtsForAllCompanies() {
        Entreprise entreprise1 = new Entreprise();
        entreprise1.setName("Company 1");
        List<Dette> dettes1 = new ArrayList<>();
        Dette dette1 = new Dette();
        dette1.setAmount(100.0);
        Dette dette2 = new Dette();
        dette2.setAmount(200.0);
        dettes1.add(dette1);
        dettes1.add(dette2);
        entreprise1.setDettes(dettes1);

        Entreprise entreprise2 = new Entreprise();
        entreprise2.setName("Company 2");
        List<Dette> dettes2 = new ArrayList<>();
        Dette dette3 = new Dette();
        dette3.setAmount(300.0);
        Dette dette4 = new Dette();
        dette4.setAmount(400.0);
        dettes2.add(dette3);
        dettes2.add(dette4);
        entreprise2.setDettes(dettes2);

        List<Entreprise> entreprises = new ArrayList<>();
        entreprises.add(entreprise1);
        entreprises.add(entreprise2);

        when(entrepriseRepository.findAll()).thenReturn(entreprises);

        debtProcessor.processDebtsForAllCompanies();

        assertEquals(300.0, entreprise1.getDebtAmount());
        assertEquals(700.0, entreprise2.getDebtAmount());
        verify(entrepriseRepository, times(2)).save(any(Entreprise.class));
    }
}

