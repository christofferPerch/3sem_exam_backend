package businessfacades;

import dtos.TrainingSessionDTO;
import entities.*;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSessionDTOFacadeTest {
    private static EntityManagerFactory emf;
    private static TrainingSessionDTOFacade facade;
    Category category1;
    Category category2;
    TrainingSessionDTO trainingSessionDTO1;
    TrainingSessionDTO trainingSessionDTO2;
    TrainingSession trainingSession1;
    TrainingSession trainingSession2;

    String date1 = "2022/10/29";

    TrainingSessionDTOFacadeTest() throws ParseException {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TrainingSessionDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("TrainingSession.deleteAllRows").executeUpdate();
            em.createNamedQuery("Category.deleteAllRows").executeUpdate();


            category1 = new Category(1, "Yoga");
            category2 = new Category(2, "Dans");
            em.persist(category1);
            em.persist(category2);

            trainingSession1 = new TrainingSession("Yoga Training Session!", "10:30", date1, "Høje Gladsaxe 2", category1, 20);
            trainingSession2 = new TrainingSession("Dance Training Session!", "11:30", date1, "Nørgårdsvej 20", category2, 10);
            em.persist(trainingSession1);
            em.persist(trainingSession2);
            em.getTransaction().commit();

        } finally {
            trainingSessionDTO1 = new TrainingSessionDTO(trainingSession1);
            trainingSessionDTO2 = new TrainingSessionDTO(trainingSession2);
            em.close();
        }
    }


    @Test
    void createTrainingSession() throws API_Exception, ParseException {
        TrainingSessionDTO trainingSessionDTO = new TrainingSessionDTO(new TrainingSession( "testNew","10:30", date1,"Jernbanevej 1",category2,10));
        facade.createTrainingSession(trainingSessionDTO);
        assertNotNull(trainingSessionDTO.getId());
        int actualSize = facade.getAllTrainingSessions().size();
        assertEquals(3, actualSize);
    }

    @Test
    void deleteTrainingSesssionDTO() throws API_Exception, NotFoundException {
        facade.deleteTrainingSession(trainingSession1.getId());
        int actualSize = facade.getAllTrainingSessions().size();
        assertEquals(1, actualSize);
    }

    @Test
    void getTrainingSession() throws API_Exception {
        TrainingSessionDTO actual = facade.getTrainingSession(trainingSession1.getId());
        TrainingSessionDTO expected = trainingSessionDTO1;
        assertEquals(expected,actual);
    }

    @Test
    void editTrainingSession() throws API_Exception {
        TrainingSessionDTO expected = new TrainingSessionDTO(new TrainingSession(trainingSession2.getId(), "Hacked", "11:30", date1, "Lyngby st 0", category1, 15));
        TrainingSessionDTO actual = facade.editTrainingSession(expected);
        int actualSize = facade.getAllTrainingSessions().size();
        assertEquals(expected, actual);
        assertEquals(2, actualSize);

    }

}
