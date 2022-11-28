package businessfacades;

import datafacades.TrainingSessionFacade;
import dtos.TrainingSessionDTO;
import dtos.UserDTO;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import datafacades.UserFacade;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TrainingSessionDTOFacade {

    private static TrainingSessionDTOFacade instance;
    private static TrainingSessionFacade trainingSessionFacade;

    private TrainingSessionDTOFacade() {}

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

}


