package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.TrainingSessionDTO;
import entities.Category;
import entities.TrainingSession;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class TrainingResourceTest {
     private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    Category category1, category2;
    TrainingSession trainingSession1, trainingSession2;
    TrainingSessionDTO trainingSessionDTO1,trainingSessionDTO2;

    String date1 = "2022/10/29";

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
        try {
            em.getTransaction().begin();
            em.createNamedQuery("TrainingSession.deleteAllRows").executeUpdate();
            em.createNamedQuery("Category.deleteAllRows").executeUpdate();

            category1 = new Category(1, "Yoga");
            category2 = new Category(2, "Dans");
            em.persist(category1);
            em.persist(category2);

            trainingSession1 = new TrainingSession("Yoga Training Session!", "10:30", date1, "Høje Gladsaxe 2", category1, 20);
            trainingSession2 = new TrainingSession("Dance Training Session!", "11:30", date1, "Nørgårdsvej 20", category2, 10);
            em.persist(trainingSession1);
            em.persist(trainingSession2);
            em.getTransaction().commit();
        } finally {
            trainingSessionDTO1 = new TrainingSessionDTO(trainingSession1);
            trainingSessionDTO2 = new TrainingSessionDTO(trainingSession2);
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/users/all").then().statusCode(200);
    }


    @Test
    void create() {
        TrainingSessionDTO newTrainingSessionDTO = new TrainingSessionDTO(new TrainingSession("Hacked", "11:30", date1, "Lyngby st 0", category1, 15));
        String requestBody = GSON.toJson(newTrainingSessionDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/training")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", equalTo("Hacked"));
    }

    @Test
    void deleteTrainingSession() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", trainingSession2.getId())
                .delete("/training/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void getById() {
        given()
                .contentType(ContentType.JSON)
                .get("/training/get/" + trainingSession1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(trainingSession1.getId()))
                .body("title", equalTo(trainingSession1.getTitle()));
    }

    @Test
    void updateTrainingSession() {
    }

    @Test
    void allTrainingSessions() {
        List<TrainingSessionDTO> trainingSessionDTOS;
        trainingSessionDTOS = given()
                .contentType("application/json")
                .when()
                .get("/training/all")
                .then()
                .extract().body().jsonPath().getList("", TrainingSessionDTO.class);
        assertThat(trainingSessionDTOS, containsInAnyOrder(trainingSessionDTO1,trainingSessionDTO2));
    }
    }