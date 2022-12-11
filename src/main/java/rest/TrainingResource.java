package rest;

import businessfacades.TrainingSessionDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.exceptions.UnirestException;
import dtos.TrainingSessionDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import entities.Category;
import entities.TrainingSession;
import errorhandling.API_Exception;
import org.json.JSONObject;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("training")
public class TrainingResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private TrainingSessionDTOFacade trainingFacade = TrainingSessionDTOFacade.getInstance(EMF);

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) throws API_Exception {
        TrainingSessionDTO trainingSessionDTO = GSON.fromJson(content, TrainingSessionDTO.class);
        TrainingSessionDTO trainingDTO = trainingFacade.createTrainingSession(trainingSessionDTO);
        return Response.ok().entity(GSON.toJson(trainingDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @DELETE
    @Path("/{trainingSessionId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteTrainingSession(@PathParam("trainingSessionId") int trainingSessionId) throws API_Exception {
        TrainingSessionDTO deleteMyTrainingSession = trainingFacade.deleteTrainingSession(trainingSessionId);
        return Response.ok().entity(GSON.toJson(deleteMyTrainingSession)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/get/{trainingSessionId}") //get trainingsession by id
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("trainingSessionId") int trainingSessionId) throws API_Exception {
        return Response.ok().entity(GSON.toJson(trainingFacade.getTrainingSession(trainingSessionId))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @PUT
    @Path("/update")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateTrainingSession(String content) throws EntityNotFoundException, API_Exception {
        TrainingSessionDTO updateDTO = GSON.fromJson(content, TrainingSessionDTO.class);
        TrainingSessionDTO updateTrainingSession = trainingFacade.editTrainingSession(updateDTO);
        return Response.ok().entity(GSON.toJson(updateTrainingSession)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/all") //get all
    @Produces({MediaType.APPLICATION_JSON})
    public Response allTrainingSessions() throws API_Exception {
        return Response.ok().entity(GSON.toJson(trainingFacade.getAllTrainingSessions())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/myschedule/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrainingSessionsByUser(@PathParam("userName") String userName) throws API_Exception {
        return Response.ok().entity(GSON.toJson(trainingFacade.getTrainingSessionsByUser(userName))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/distance/{origin}/{destination}")
    @Produces({MediaType.APPLICATION_JSON})
    public String distance(@PathParam("origin") String origin, @PathParam("destination") String destination) throws IOException {
        String APIKEY = System.getenv("APIKEYGOOGLE");
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&units=metric&key="+APIKEY);


        String responseString = "";
        HttpURLConnection MyConn = (HttpURLConnection) url.openConnection();
        // Set the request method to "GET"
        MyConn.setRequestMethod("GET");

        // Collect the response code
        int responseCode = MyConn.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == MyConn.HTTP_OK) {
            // Create a reader with the input stream reader.
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    MyConn.getInputStream()));
            String inputLine;

            // Create a string buffer
            StringBuffer response = new StringBuffer();

            // Write each of the input line
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //Show the output
            responseString=response.toString();
        } else {
            responseString="Error found";
        }

        return responseString;
    }

    @POST
    @Path("/sendReminder")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response sendEmailToAllUsersFromTrainingSession(String content) throws API_Exception, UnirestException {
        TrainingSessionDTO trainingSessionDTO = GSON.fromJson(content, TrainingSessionDTO.class);
        return Response.ok().entity(GSON.toJson(trainingFacade.sendEmailToAllUsers(trainingSessionDTO.getId()))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}