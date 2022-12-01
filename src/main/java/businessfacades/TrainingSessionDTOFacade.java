package businessfacades;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import datafacades.TrainingSessionFacade;
import dtos.TrainingSessionDTO;
import entities.TrainingSession;
import entities.User;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

public class TrainingSessionDTOFacade {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final String APK_KEY = ""; //API KEY MUST BE SAVED IN SEPERATED FOLDER AND NOT BE DEPLOYED TO GITHUB!!!!!

    private UserDTOFacade userFacade = UserDTOFacade.getInstance(EMF);
    private static TrainingSessionDTOFacade instance;
    private static TrainingSessionFacade trainingSessionFacade;

    private TrainingSessionDTOFacade() {
    }

    public static TrainingSessionDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            trainingSessionFacade = TrainingSessionFacade.getUserFacade(_emf);
            instance = new TrainingSessionDTOFacade();
        }
        return instance;
    }

    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO) throws API_Exception {
        return new TrainingSessionDTO(trainingSessionFacade.createTrainingSession(trainingSessionDTO.getEntity()));
    }

    public TrainingSessionDTO deleteTrainingSession(int id) throws API_Exception {
        return new TrainingSessionDTO(trainingSessionFacade.deleteTrainingSession(id));
    }

    public TrainingSessionDTO getTrainingSession(int id) throws API_Exception {
        return new TrainingSessionDTO(trainingSessionFacade.getTrainingSession(id));
    }

    public TrainingSessionDTO editTrainingSession(TrainingSession trainingSession) throws API_Exception {
        return new TrainingSessionDTO(trainingSessionFacade.editTrainingSession(trainingSession));
    }

    public String sendEmailToAllUsers(int trainingSessionId) throws UnirestException, API_Exception {
        TrainingSession trainingSession = trainingSessionFacade.getTrainingSession(trainingSessionId);
        for (User user : trainingSession.getUsers()) {
            if (trainingSession.getId().equals(trainingSessionId)) {
                String YOUR_DOMAIN_NAME = "sandbox06d065eea4cc4b92bbc153a5d24516e9.mailgun.org";
                HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                        .basicAuth("api", APK_KEY)
                        .queryString("from", "You have an upcoming training session! trainer@lyngbys.me")
                        .queryString("to", user.getUserEmail())
                        .queryString("subject", "You have an upcoming training session!")
                        .queryString("text", "Training session starting at " + trainingSession.getTime() + " on date " + trainingSession.getTime())
                        .asJson();
            }

        }
        return "Done";
    }

}


