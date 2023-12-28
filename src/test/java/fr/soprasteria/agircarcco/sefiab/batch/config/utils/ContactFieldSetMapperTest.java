package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactFieldSetMapperTest {

    private ContactFieldSetMapper contactFieldSetMapper;
    private FieldSet fieldSet;

    @BeforeEach
    public void setUp() {
        contactFieldSetMapper = new ContactFieldSetMapper();
        fieldSet = mock(FieldSet.class);
    }

    @Test
    public void mapFieldSet_AllFieldsPresent() throws BindException {
        when(fieldSet.readLong("contact_id")).thenReturn(1L);
        when(fieldSet.readString("name")).thenReturn("Test Name");
        when(fieldSet.readString("first_name")).thenReturn("John");
        when(fieldSet.readString("last_name")).thenReturn("Doe");
        when(fieldSet.readLong("entreprise_id")).thenReturn(2L);

        Contact contact = contactFieldSetMapper.mapFieldSet(fieldSet);

        assertThat(contact).isNotNull();
        assertThat(contact.getId()).isEqualTo(1L);
        assertThat(contact.getName()).isEqualTo("Test Name");
        assertThat(contact.getFirstName()).isEqualTo("John");
        assertThat(contact.getLastName()).isEqualTo("Doe");
        assertThat(contact.getEntreprise()).isNotNull();
        assertThat(contact.getEntreprise().getId()).isEqualTo(2L);
    }

    @Test
    public void mapFieldSet_MissingFields() throws BindException {
        when(fieldSet.readLong("contact_id")).thenReturn(1L);
        when(fieldSet.readString("name")).thenReturn("Test Name");
        when(fieldSet.readString("first_name")).thenThrow(new IllegalArgumentException("Field not present"));

        // Expect an exception when a required field is missing
        assertThatThrownBy(() -> contactFieldSetMapper.mapFieldSet(fieldSet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Field not present");
    }

    @Test
    public void mapFieldSet_TypeMismatch() throws BindException {
        when(fieldSet.readLong("contact_id")).thenReturn(1L);
        when(fieldSet.readString("name")).thenReturn("Test Name");
        when(fieldSet.readString("first_name")).thenReturn("John");
        when(fieldSet.readString("last_name")).thenReturn("Doe");
        when(fieldSet.readLong("entreprise_id")).thenThrow(new NumberFormatException("Field type mismatch"));

        // Expect an exception when there's a type mismatch
        assertThatThrownBy(() -> contactFieldSetMapper.mapFieldSet(fieldSet))
                .isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("Field type mismatch");
    }
}

