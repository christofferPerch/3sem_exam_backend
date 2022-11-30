package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import entities.Address;
import entities.CityInfo;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    UserDTO udto1, udto2;
    CityInfo c1,c2;
    Address a1,a2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/users/all").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/users/all")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/users/all")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testPrintResponse() {
        Response response = given().when().get("/users/" + udto1.getUserName());
        ResponseBody body = response.getBody();
        System.out.println(body.prettyPrint());

        response
                .then()
                .assertThat()
                .body("userName", equalTo("Oscar"));
    }


    @Test
    void allUsers() {
        List<UserDTO> usersDTOS;
        usersDTOS = given()
                .contentType("application/json")
                .when()
                .get("/users/all")
                .then()
                .extract().body().jsonPath().getList("", UserDTO.class);
        assertThat(usersDTOS, containsInAnyOrder(udto1, udto2));
    }


    @Test
    void getUserByUserName() {
        given()
                .contentType(ContentType.JSON)
                .get("/users/" + udto1.getUserName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("userName", equalTo(udto1.getUserName()))
                .body("roleList", containsInAnyOrder(udto1.getRoleList().get(0)));
    }

    @Test
    void createUser() {
        User user = new User();
        user.setUserName("Chris");
        user.setUserPass("PW");
        user.setUserEmail("chris@gmail.com");
        Role role = new Role("user");
        user.addRole(role);
        CityInfo c = new CityInfo(2750,"Ballerup");
        Address a = new Address("testvej",c);
        user.setAddress(a);

        UserDTO udto = new UserDTO(user);
        String requestBody = GSON.toJson(udto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .body("userName", equalTo("Chris"))
                .body("roleList", containsInAnyOrder("user"));


    }

    @Test
    void deleteUser() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("userName", udto1.getUserName())
                .delete("/users/{userName}")
                .then()
                .statusCode(200);
    }

    @Test
    void updateUser() {
        udto1.setUserEmail("nyemail@gmail.com");
        udto1.setRoleList(new ArrayList<>());
        given()
                .header("Content-type", ContentType.JSON)
                .body(GSON.toJson(udto1))
                .when()
                .put("/users/" + udto1.getUserName())
                .then()
                .assertThat()
                .statusCode(200)
                .body("userName", equalTo("Oscar"))
                .body("userEmail", equalTo("nyemail@gmail.com"));
    }


}