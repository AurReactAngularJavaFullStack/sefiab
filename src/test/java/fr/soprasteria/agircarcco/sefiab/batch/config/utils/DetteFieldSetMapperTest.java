package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class DetteFieldSetMapperTest {

    @InjectMocks
    private DetteFieldSetMapper detteFieldSetMapper;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private FieldSet fieldSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapFieldSet_ValidFields() throws BindException {
        Entreprise mockEntreprise = new Entreprise();
        mockEntreprise.setId(1L);

        when(fieldSet.readLong("dette_id")).thenReturn(1L);
        when(fieldSet.readDouble("amount")).thenReturn(1000.50);
        when(fieldSet.readLong("entreprise_id")).thenReturn(1L);
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(mockEntreprise));

        Dette dette = detteFieldSetMapper.mapFieldSet(fieldSet);

        assertThat(dette).isNotNull();
        assertThat(dette.getId()).isEqualTo(1L);
        assertThat(dette.getAmount()).isEqualTo(1000.50);
        assertThat(dette.getEntreprise()).isEqualTo(mockEntreprise);
    }

    @Test
    public void mapFieldSet_MissingEntreprise() throws BindException {
        // Mock the FieldSet values
        when(fieldSet.readLong("dette_id")).thenReturn(1L);
        when(fieldSet.readLong("entreprise_id")).thenReturn(1L);

        // Mock the EntrepriseRepository to return no Entreprise for the given ID
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert that the exception is thrown with the correct message
        assertThatThrownBy(() -> detteFieldSetMapper.mapFieldSet(fieldSet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No Entreprise found for id: 1");
    }


    @Test
    public void mapFieldSet_TypeMismatch() throws BindException {
        when(fieldSet.readLong("dette_id")).thenReturn(1L);
        when(fieldSet.readDouble("amount")).thenThrow(new NumberFormatException("Field type mismatch"));

        assertThatThrownBy(() -> detteFieldSetMapper.mapFieldSet(fieldSet))
                .isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("Field type mismatch");
    }

    // Vous pouvez ajouter d'autres cas de test selon vos besoins.
}
