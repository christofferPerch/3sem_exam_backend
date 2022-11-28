package dtos;

import entities.Address;
import entities.Role;
import entities.TrainingSession;
import entities.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {

    private String userName;
    private String userEmail;
    private String userPass;
    private AddressDTO address;
    private List<TrainingSession> trainingSessions;
    private List<String> roleList;

    public UserDTO(User user){
        if(user.getUserName() != null){
            this.userName = user.getUserName();
        }
        this.userEmail = user.getUserEmail();
        this.userPass = user.getUserPass();
        this.address = new AddressDTO(user.getAddress());
        this.roleList = user.getRolesAsStrings();
    }

    public User getEntity(){
        User user = new User();
        if(this.userName != null){
            user.setUserName(this.userName);
        }
        user.setUserEmail(this.userEmail);
        user.setUserPass(this.userPass);
        user.setAddress(this.address.getEntity());
        user.getRolesAsStrings();
        return user;
    }

    public static List<UserDTO> getUserDTOs(List<User> users){
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user->userDTOs.add(new UserDTO(user)));
        return userDTOs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return getUserName().equals(userDTO.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", address=" + address +
                ", trainingSessions=" + trainingSessions +
                ", roleList=" + roleList +
                '}';
    }
}
