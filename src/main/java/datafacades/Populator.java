package datafacades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Category;
import entities.Role;
import entities.TrainingSession;
import entities.User;
import utils.EMF_Creator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Populator {
    public static void populate() throws ParseException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String myDate = "2022/10/29 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        java.sql.Timestamp date1= new Timestamp(millis);

        User user = new User("user", "user@gmail.com","test123");
        User admin = new User("admin", "admin@gmail.com","test123");
        Category c1 = new Category("Yoga");
        Category c2 = new Category("Dans");
        TrainingSession t1 = new TrainingSession("Hot Yoga","10.00",date1,"Københavns yoga akademi",c1,10);
        TrainingSession t2 = new TrainingSession("Hot Dans","12.00",date1,"Ålborgs danse akademi",c2,12);


        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        user.addTrainingSession(t1);
        admin.addRole(adminRole);
        admin.addTrainingSession(t2);
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
