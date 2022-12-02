package datafacades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.*;
import utils.EMF_Creator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Populator {
    public static void populate() throws ParseException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String date1 = "2022/10/29";

        User user = new User("user", "user@gmail.com","test123");
        User admin = new User("admin", "admin@gmail.com","test123");
        Category c1 = new Category("Yoga");
        Category c2 = new Category("Dans");
        TrainingSession t1 = new TrainingSession("Hot Yoga","10.00",date1,"Københavns yoga akademi",c1,10);
        TrainingSession t2 = new TrainingSession("Hot Dans","12.00",date1,"Ålborgs danse akademi",c2,12);
        CityInfo city1 = new CityInfo(2750,"Ballerup");
        CityInfo city2 = new CityInfo(2800,"Kongens Lyngby");
        Address a1 = new Address("Sankt Jacobsvej",city1);
        Address a2 = new Address("Nørgaardsvej",city2);


        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        user.addTrainingSession(t1);
        user.setAddress(a1);
        admin.addRole(adminRole);
        admin.addTrainingSession(t2);
        admin.setAddress(a2);
        em.persist(a1);
        em.persist(a2);
        em.persist(c1);
        em.persist(c2);
        em.persist(t1);
        em.persist(t2);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }
    
    public static void main(String[] args) throws ParseException {
        populate();
    }
}
