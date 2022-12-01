package rest;

import businessfacades.TrainingSessionDTOFacade;
import businessfacades.UserDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.exceptions.UnirestException;
import dtos.TrainingSessionDTO;
import dtos.UserDTO;

import java.nio.charset.StandardCharsets;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import entities.TrainingSession;
import errorhandling.API_Exception;
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
}