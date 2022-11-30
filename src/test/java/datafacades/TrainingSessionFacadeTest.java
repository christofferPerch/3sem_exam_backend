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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSessionFacadeTest {
    private static EntityManagerFactory emf;
    private static TrainingSessionFacade facade;
    private static TrainingSession ts1;

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
        ts1= new TrainingSession("test","10:30", new Date(20221201),"jernbanevej 1", null,10);
        try{
            em.getTransaction().begin();
            Category category;//=em.find(Category.class, 1);
            TypedQuery<Category> query = em.createQuery("select c from Category c where c.categoryName=:name",Category.class);
            query.setParameter("name","Yoga");
            category = query.getSingleResult();
            System.out.println(category);
//            ts1.setCategory(category);
//            em.persist(ts1);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
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
    void deleteTrainingSession() throws API_Exception {
        facade.deleteTrainingSession(1);
        int actualSize = facade.getAllTrainingSessions().size();
        assertEquals(1, actualSize);
    }

    @Test
    void getTrainingSession() {
    }

    @Test
    void deregisterTrainingSession(){

    }
}