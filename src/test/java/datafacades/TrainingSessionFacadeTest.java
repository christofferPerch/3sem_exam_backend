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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainingSessionFacadeTest {
    private static EntityManagerFactory emf;
    private static TrainingSessionFacade facade;
    Category category1;
    Category category2;
    TrainingSession trainingSession1;
    TrainingSession trainingSession2;

    String myDate = "2022/10/29 18:10:45";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = sdf.parse(myDate);

    long millis = date.getTime();
    java.sql.Timestamp date1= new Timestamp(millis);

    TrainingSessionFacadeTest() throws ParseException {
    }

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
            em.close();
        }
    }

    @Test
    void createTrainingSession() throws API_Exception, ParseException {
        TrainingSession trainingSession = new TrainingSession("test","10:30", date1,"Jernbanevej 1",category2,10);
        System.out.println(trainingSession);
        facade.createTrainingSession(trainingSession);
    }
    @Test
    void deleteTrainingSession() throws API_Exception {
        facade.deleteTrainingSession(trainingSession1.getId());
        int actualSize = facade.getAllTrainingSessions().size();
        assertEquals(1, actualSize);
    }

    @Test
    void getTrainingSession() throws API_Exception {
        TrainingSession actual = facade.getTrainingSession(trainingSession1.getId());
        TrainingSession expected = trainingSession1;
        assertEquals(expected,actual);
    }

    @Test
    void editTrainingSession() throws API_Exception {
        trainingSession1 = new TrainingSession(1, "Dance Training Session!", "11:30", date1, "Nørgårdsve 0", category1, 10);
        TrainingSession expected = trainingSession1;
        TrainingSession actual = facade.editTrainingSession(expected);
        assertEquals(expected, actual);

    }
    @Test
    void deregisterTrainingSession(){

    }
}