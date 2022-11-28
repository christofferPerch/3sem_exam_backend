package businessfacades;

import dtos.UserDTO;
import entities.Address;
import entities.CityInfo;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserDTOFacadeTest {

    private static EntityManagerFactory emf;
    private static UserDTOFacade facade;

    UserDTO udto1, udto2;
    CityInfo c1,c2;
    Address a1,a2;
    public UserDTOFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("user");
        User u1 = new User();
        User u2 = new User();
        c1 = new CityInfo(2750,"Ballerup");
        c2 = new CityInfo(2800,"Lyngby");
        a1 = new Address("sankt jacobsvej",c1);
        a2 = new Address("nørgardsvej",c2);
        u1.setUserName("Oscar");
        u1.setUserPass("test");
        u1.setUserEmail("Oscar@gmail.com");
        u1.addRole(userRole);
        u1.setAddress(a1);

        u2.setUserName("Mark");
        u2.setUserPass("test");
        u2.setUserEmail("Mark@gmail.com");
        u2.addRole(userRole);
        u2.setAddress(a2);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(userRole);
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(u1);
            em.persist(u2);
            em.getTransaction().commit();
        } finally {
            udto1 = new UserDTO(u1);
            udto2 = new UserDTO(u2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void createUserDTOTest() throws API_Exception {
        UserDTO userDTO = new UserDTO(new User("Chris","Chris@gmail.com","PW",
                new Address("sankt jacobsvej",new CityInfo(2750,"Ballerup"))));


        facade.createUser(userDTO);
        assertNotNull(userDTO.getUserName());
        int actualSize = facade.getAllUsers().size();
        assertEquals(3, actualSize);
    }



    @Test
    void createNoDuplicateUserDTOs() throws API_Exception {
        UserDTO userDTO = new UserDTO(new User("Oscar", "Oscar@gmail.com","PW",new Address("testgade",
                new CityInfo(2750,"Ballerup"))));
        assertThrows(API_Exception.class, () -> facade.createUser(userDTO));
    }

    @Test
    void findUserDTOByUsername() throws API_Exception {
        UserDTO userDTO = facade.getUserByUserName(udto1.getUserName());
        assertEquals(udto1, userDTO);
    }

    @Test
    void findAllUserDTOs() throws API_Exception {
        List<UserDTO> actual = facade.getAllUsers();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

    @Test
    void deleteUserDTO() throws API_Exception, NotFoundException {
        facade.deleteUser("Oscar");
        int actualSize = facade.getAllUsers().size();
        assertEquals(1, actualSize);
    }

    @Test
    void updateUserDTO() throws API_Exception {
        UserDTO expected = new UserDTO(udto1.getEntity());
        expected.setUserEmail("Testefar@test.dk");
        UserDTO actual = facade.updateUser(expected);
        assertEquals(expected,actual);
    }

    @Test
    void CantFindUserDTOToDelete() {
        assertThrows(API_Exception.class, () -> facade.deleteUser("HEJSA"));
    }

}


