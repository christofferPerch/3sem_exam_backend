package datafacades;

import entities.Category;
import entities.TrainingSession;
import errorhandling.API_Exception;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSessionFacadeTest {
    private static EntityManagerFactory emf;
    private static TrainingSessionFacade facade;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TrainingSessionFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    void createTrainingSession() throws API_Exception {
        Category category1 = new Category("Yoga");
        //date doesn't work correctly:
        TrainingSession trainingSession = new TrainingSession("test","10:30", new Date(2022-12-1),"Jernbanevej 1",category1,10);
        System.out.println(trainingSession);
        facade.createTrainingSession(trainingSession);
    }

    @Test
    void getTrainingSession() {
    }
}