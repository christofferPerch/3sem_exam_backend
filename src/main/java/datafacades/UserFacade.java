package datafacades;

import dtos.UserDTO;
import entities.Address;
import entities.CityInfo;
import entities.Role;
import entities.User;

import javax.persistence.*;

import errorhandling.API_Exception;
import org.mindrot.jbcrypt.BCrypt;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName= :username", User.class);
            query.setParameter("username", username);
            user = query.getSingleResult();

            if (/* user == null ||*/ !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } catch (NoResultException e) {
            throw new AuthenticationException("Invalid username or password");
        } finally {
            em.close();
        }
        return user;
    }


    public User createUser(User user) throws API_Exception {
        EntityManager em = getEntityManager();
        user.addRole(new Role("user")); //Can also be done in frontend

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("There's already a user with the username: " + user.getUserName() + " in the system!");
        } finally {
            em.close();
        }
        return user;
    }


//    public User updateUser(User user) throws API_Exception {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            user.addRole(new Role("user")); //Could make a new method where you can assign new roles, otherwise it's always "user"
//            em.merge(user);
//            em.getTransaction().commit();
//            return user;
//        } catch (Exception e) {
//            throw new API_Exception("Can't find a user with the username: "+user.getUserName(),400,e);
//        } finally {
//            em.close();
//        }
//    }


    public User updateUser(User user) throws API_Exception {
        EntityManager em = getEntityManager();
        user.addRole(new Role("user"));     //Could make a new method where you can assign new roles, otherwise it's always "user"
        em.find(User.class, user.getUserName());
        if (user.getAddress().getStreetAddress().length() != 0) {
            user.getAddress().setStreetAddress(user.getAddress().getStreetAddress());      // Should work in FrontEnd if you don't update ur address.
        }

        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new API_Exception("Can't find a user with the username: " + user.getUserName(), 400, e);
        } finally {
            em.close();
        }

    }


    public User getUserByUserName(String userName) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            User u = em.find(User.class, userName);
            return u;
        } catch (Exception e) {
            throw new API_Exception("Can't find a user with the username: " + userName, 404, e);
        }

    }

    public List<User> getAllUsers() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any users in the system", 404, e);
        }
    }

    public User deleteUser(String userName) throws API_Exception {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userName);

        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (user == null) {
                throw new API_Exception("Can't find a user with the username: " + userName, 400, e);
            }
        } finally {
            em.close();
        }
        return user;
    }

//    public void deleteAddress(int addressId) throws API_Exception {
//        EntityManager em = getEntityManager();
//        Address address = em.find(Address.class, addressId);
//
//        try {
//            em.getTransaction().begin();
//            em.remove(address);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (address == null) {
//                throw new API_Exception("Can't find a user with the address: " + address, 400, e);
//            }
//        } finally {
//            em.close();
//        }
//    }

    public static void main(String[] args) throws API_Exception {
        emf = EMF_Creator.createEntityManagerFactory();
        UserFacade uf = getUserFacade(emf);
//        System.out.println(uf.getAllUsers());
//        System.out.println(uf.getUserByUserName("user"));
//        System.out.println(uf.deleteUser("test"));
//        uf.deleteAddress(11);

    }
}
