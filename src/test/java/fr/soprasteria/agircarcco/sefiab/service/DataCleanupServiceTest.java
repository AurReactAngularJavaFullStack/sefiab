package fr.soprasteria.agircarcco.sefiab.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataCleanupServiceTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private DataCleanupService dataCleanupService;

    @Mock
    private Query query;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testPerformCleanup() {
        // Set up stubs required for this specific test
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        dataCleanupService.performCleanup();

        verify(sessionFactory, times(3)).openSession(); // Session factory should be called three times
        verify(session, times(3)).beginTransaction();  // Begin transaction should be called three times
        verify(session, times(3)).createQuery(anyString());  // Query should be created 3 times
        verify(transaction, times(3)).commit();        // Commit should be called three times
    }


    // Ici, vous pouvez ajouter d'autres tests pour gérer différents scénarios,
    // comme ce qui devrait se passer en cas d'exception, etc.
    @Test
    public void testPerformCleanupExceptionThrown() {
        // Ensure that the session mock is properly initialized and stubbed to return when sessionFactory.openSession() is called
        when(sessionFactory.openSession()).thenReturn(session);

        // Make the createQuery method throw an exception
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Test Exception"));

        // If you have logic that calls beginTransaction, set up a stub for that as well.
        when(session.beginTransaction()).thenReturn(transaction);

        // You might want to handle or catch the exception if it affects your test
        try {
            dataCleanupService.performCleanup();
        } catch (RuntimeException e) {
            // Expected exception, so do nothing or you can add some assertions here.
        }

        verify(sessionFactory, times(3)).openSession();      // Session should be opened 3 times
        verify(session, times(3)).beginTransaction();       // Transaction should start 3 times
        verify(session, times(3)).createQuery(anyString());   // Query creation attempted 3 times
        verify(transaction, never()).commit();     // Commit should never be called
        verify(transaction, times(3)).rollback();  // Rollback should be called 3 times due to exception
    }





    @Test
    public void testCleanupSequenceForEntities() {
        // Mock the Query class to avoid NullPointerException
        Query mockQuery = mock(Query.class);

        // Stub the sessionFactory to return the mocked session
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        // Stub the createQuery method on session to return the mocked query
        when(session.createQuery(anyString())).thenReturn(mockQuery);

        // Execute the method under test
        dataCleanupService.performCleanup();

        // Verifications
        InOrder inOrder = inOrder(session);
        inOrder.verify(session).createQuery("DELETE FROM Contact WHERE someConditionForContact = :conditionValue");
        inOrder.verify(session).createQuery("DELETE FROM Dette WHERE someConditionForDette = :conditionValue");
        inOrder.verify(session).createQuery("DELETE FROM Entreprise WHERE someConditionForEntreprise = :conditionValue");
    }

    @Test
    public void testNoRowsDeleted() {
        // Mock the Query class to avoid NullPointerException
        Query mockQuery = mock(Query.class);

        // Stub the createQuery method on session to return the mocked query
        when(session.createQuery(anyString())).thenReturn(mockQuery);

        // Stub the executeUpdate method on mockQuery to return 0
        when(mockQuery.executeUpdate()).thenReturn(0);

        // Stub the setParameter method on mockQuery to return the mockQuery object itself
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);

        // Stub the sessionFactory to return the mocked session
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        // Execute the method under test
        dataCleanupService.performCleanup();

        // Verifications
        verify(sessionFactory, times(3)).openSession();
        verify(session, times(3)).createQuery(anyString());
        verify(transaction, times(3)).commit();
        // Add more verifications as necessary
    }




    @Test
    public void testSessionClosedAfterCleanup() {
        // Mock the Query class to avoid NullPointerException
        Query mockQuery = mock(Query.class);

        // Stub the sessionFactory to return the mocked session
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);  // This line ensures beginTransaction() doesn't throw an error

        // Stub the createQuery method on session to return the mocked query
        when(session.createQuery(anyString())).thenReturn(mockQuery);

        // Ensure the setParameter method on the mockQuery returns the mockQuery object itself
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);

        // Execute the method under test
        dataCleanupService.performCleanup();

        // Verifications
        // Ensure sessionFactory.openSession() is called the expected number of times.
        verify(sessionFactory, times(3)).openSession();

        // Ensure session.close() is called the expected number of times.
        verify(session, times(3)).close();
    }



    @Test
    public void testSessionNotOpenedIfInitialConditionNotMet() {
        DataCleanupService customService = new DataCleanupService() {
            @Override
            protected boolean initialConditionIsMet() {
                return false;
            }
        };
        customService.setSessionFactory(sessionFactory); // assuming you have a setter for sessionFactory

        customService.performCleanup();

        verify(sessionFactory, never()).openSession();
        verify(session, never()).beginTransaction();
    }



}
