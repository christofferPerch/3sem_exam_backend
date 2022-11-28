package datafacades;

import entities.Category;
import entities.Role;
import entities.TrainingSession;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.time.Instant;
import java.util.List;


public class TrainingSessionFacade {

    private static EntityManagerFactory emf;
    private static TrainingSessionFacade instance;

    private TrainingSessionFacade() {
    }


    public static TrainingSessionFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TrainingSessionFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TrainingSession createTrainingSession(TrainingSession trainingSession) throws API_Exception {
        EntityManager em = getEntityManager();
        //TrainingSession trainingSession = new TrainingSession(id, title, time, date,fullAddress, category, maxParticipants, users);
        try {
            em.getTransaction().begin();
            em.persist(trainingSession);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("There's already a user with the username: " + trainingSession.getId() + " in the system!");
        } finally {
            em.close();
        }
        return trainingSession;
    }

}
