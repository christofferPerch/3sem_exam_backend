package datafacades;

import entities.TrainingSession;

import javax.persistence.*;

import entities.User;
import errorhandling.API_Exception;

import java.util.List;


public class TrainingSessionFacade {

    private static EntityManagerFactory emf;
    private static TrainingSessionFacade instance;

    private TrainingSessionFacade() {
    }


    public static TrainingSessionFacade getTrainingSessionFacade(EntityManagerFactory _emf) {
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
            TypedQuery<TrainingSession> query = em.createQuery("SELECT t FROM TrainingSession t", TrainingSession.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("could not find any training sessions", 404, e);
        }
    }

    // Mangler at lave test metode til.
    public List<TrainingSession> getTrainingSessionsByUser(String userName) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<TrainingSession> query = em.createQuery("SELECT ts FROM TrainingSession ts JOIN ts.users u " +
                    "WHERE u.userName = :userName", TrainingSession.class);
            query.setParameter("userName", userName);
            List<TrainingSession> trainingSessions = query.getResultList();
            return trainingSessions;
        } finally {
            em.close();
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
