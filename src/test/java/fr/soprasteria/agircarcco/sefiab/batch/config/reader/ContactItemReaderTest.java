package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContactItemReaderTest {

    private final Long expectedThirdContactId = 3L; // Or whatever the expected ID value is

    private ContactItemReader contactItemReader;

    @BeforeEach
    public void setUp() {
        contactItemReader = new ContactItemReader();
    }

    @Test
    public void testReadThirdContact() throws Exception {
        FlatFileItemReader<Contact> reader = contactItemReader.contactReader();
        reader.open(new ExecutionContext());

        reader.read(); // Premier contact
        reader.read(); // Deuxième contact

        Contact thirdContact = reader.read();
        assertThat(thirdContact).isNotNull();
        // Assumez que vous avez des valeurs attendues pour le troisième contact.
        assertThat(thirdContact.getId()).isEqualTo(expectedThirdContactId);
        // ... autres assertions ...

        reader.close();
    }

    @Test
    public void testEndOfFileAfterReadingAllContacts() throws Exception {
        FlatFileItemReader<Contact> reader = contactItemReader.contactReader();
        reader.open(new ExecutionContext());

        Contact contact;
        int count = 0;
        while ((contact = reader.read()) != null) {
            count++;
        }

        // Supposez que vous ayez 10 contacts dans votre fichier CSV.
        assertThat(count).isEqualTo(50);

        reader.close();
    }

    @Test
    public void testHandlingOfMalformedLines() {
        FlatFileItemReader<Contact> reader = contactItemReader.contactReader();
        reader.setResource(new ClassPathResource("malformed_contact.csv")); // Un fichier CSV mal formaté
        reader.open(new ExecutionContext());

        // Attendez-vous à une exception lors de la lecture d'une ligne mal formatée.
        assertThatThrownBy(() -> {
            reader.read();
        }).isInstanceOf(FlatFileParseException.class);

        reader.close();
    }

    @Test
    public void testMissingCSVFile() {
        FlatFileItemReader<Contact> reader = contactItemReader.contactReader();
        reader.setResource(new ClassPathResource("missing_contact.csv")); // Un fichier CSV inexistant

        assertThatThrownBy(() -> {
            reader.open(new ExecutionContext());
        }).isInstanceOf(ItemStreamException.class);


        reader.close();
    }
}
