package businessfacades;

import dtos.UserDTO;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import datafacades.UserFacade;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserDTOFacade {

    private static UserDTOFacade instance;
    private static UserFacade userFacade;

    private UserDTOFacade() {}

    public static UserDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            userFacade = UserFacade.getUserFacade(_emf);
            instance = new UserDTOFacade();
        }
        return instance;
    }

    public UserDTO getVerifiedUser(String username, String userpass) throws AuthenticationException {
        return new UserDTO(userFacade.getVerifiedUser(username,userpass));
    }
    public UserDTO createUser(UserDTO userDTO) throws API_Exception {
        return new UserDTO(userFacade.createUser(userDTO.getEntity()));
    }
    public UserDTO updateUser(UserDTO userDTO) throws API_Exception {
        return new UserDTO(userFacade.updateUser(userDTO.getEntity()));
    }
    public UserDTO getUserByUserName(String userName) throws API_Exception {
        return new UserDTO(userFacade.getUserByUserName(userName));
    }
    public List<UserDTO> getAllUsers() throws API_Exception {
        return UserDTO.getUserDTOs(userFacade.getAllUsers());
    }
    public UserDTO deleteUser(String userName) throws API_Exception {
        return new UserDTO(userFacade.deleteUser(userName));
    }
}
