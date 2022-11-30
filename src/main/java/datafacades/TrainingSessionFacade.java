package datafacades;

import entities.Category;
import entities.Role;
import entities.TrainingSession;
import entities.User;

import javax.persistence.*;

import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.time.Instant;
import java.util.ArrayList;
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
            throw new API_Exception("This is an error " + trainingSession.getId() + "!");
        } finally {
            em.close();
        }
        return trainingSession;
    }

    public TrainingSession getTrainingSession(int id) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TrainingSession> query = em.createQuery("SELECT t FROM TrainingSession t where t.id=:id", TrainingSession.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e){
            throw new API_Exception("Can't find any users in the system",404,e);
        }
    }


    public void deleteTrainingSession(int id) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TrainingSession tstoRemove = em.find(TrainingSession.class,id);
            em.remove(tstoRemove);
            em.getTransaction().commit();
        } catch (Exception e){
            throw new API_Exception("Can't find any training sessions in the system with that id",404,e);
        }
    }

}
