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
import java.util.Collection;
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
        try {
            em.getTransaction().begin();
            em.merge(trainingSession);
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
            //replace with em.find
            TypedQuery<TrainingSession> query = em.createQuery("SELECT t FROM TrainingSession t where t.id=:id", TrainingSession.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new API_Exception("Can't find any users in the system", 404, e);
        }
    }


    public TrainingSession deleteTrainingSession(int id) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TrainingSession tstoRemove = em.find(TrainingSession.class, id);
            em.getTransaction().begin();
            em.remove(tstoRemove);
            em.getTransaction().commit();
            return tstoRemove;
        } catch (Exception e) {
            throw new API_Exception("could not remove training session with id: " + id, 404, e);
        } finally {
            em.close();
        }
    }

    public List<TrainingSession> getAllTrainingSessions() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TrainingSession> query = em.createQuery("SELECT t FROM TrainingSession  t", TrainingSession.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("could not find any training sessions", 404, e);
        }
    }

    public TrainingSession editTrainingSession(TrainingSession trainingSession) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.find(TrainingSession.class, trainingSession.getId());
            em.getTransaction().begin();
            em.merge(trainingSession);
            em.getTransaction().commit();
            return trainingSession;
        } catch (Exception e) {
            throw new API_Exception("fail", 404, e);
        }finally {
            em.close();
        }
    }
}
