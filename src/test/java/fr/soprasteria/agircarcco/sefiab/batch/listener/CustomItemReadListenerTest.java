//package fr.soprasteria.agircarcco.sefiab.batch.listener;
//
//import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@PrepareForTest
//public class CustomItemReadListenerTest {
//    private CustomItemReadListener<Entreprise> listener;
//    private Logger logger = Mockito.mock(Logger.class);
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        MockedStatic<LoggerFactory> mocked = Mockito.mockStatic(LoggerFactory.class);
//        mocked.when(() -> LoggerFactory.getLogger(CustomItemReadListener.class)).thenReturn(logger);
//        listener = new CustomItemReadListener<>();
//
//        // Utilisez la réflexion pour injecter le mock du logger
////        Field loggerField = CustomItemReadListener.class.getDeclaredField("log");
////        loggerField.setAccessible(true);
////        loggerField.set(listener, logger);
////        PowerMockito.mockStatic(LoggerFactory.class);
////        Mockito.when(LoggerFactory.getLogger(CustomItemReadListener.class)).thenReturn(logger);
//    }
//
//    @Test
//    public void testBeforeRead() {
//        listener.beforeRead();
//        Mockito.verify(logger).debug("About to read an item.");
//    }
//
//    @Test
//    public void testAfterRead() {
//        Entreprise entreprise = new Entreprise();  // Remplacez cela par un mock ou une instance réelle si nécessaire.
//        listener.afterRead(entreprise);
//        Mockito.verify(logger).debug("Successfully read an item: {}", entreprise);
//    }
//
//    @Test
//    public void testOnReadError() {
//        Exception exception = new Exception("Test read exception");
//        listener.onReadError(exception);
//        Mockito.verify(logger).error("Error while reading item.", exception);
//    }
//}
//
//
