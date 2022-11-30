package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_email")
    private String userEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;

    @ManyToOne( fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @JoinTable(name = "user_trainingsessions", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "trainingsession_id", referencedColumnName = "trainingsession_id")})
    @ManyToMany
    private List<TrainingSession> trainingSessions = new ArrayList<>();


    public User() {
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User(String userName, String userPass, List<Role> roleList) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        this.roleList = roleList;
    }

    public User(String userName, String userEmail, String userPass) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User(String userName, String userEmail, String userPass, Address address) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.address = address;
    }

    public User(String userName, String userEmail, String userPass, Address address, List<Role> roleList, List<TrainingSession> trainingSessions) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.address = address;
        this.roleList = roleList;
        this.trainingSessions = trainingSessions;
    }

    public List<String> getTrainingsAsStrings(){
        if(trainingSessions.isEmpty()) {
            return null;
        }
        List<String> trainingsAsStrings = new ArrayList<>();
        trainingSessions.forEach((ts ->{
            trainingsAsStrings.add(ts.getTitle());
        }));
        return trainingsAsStrings;
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, userPass));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPass() {
        return this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public void addTrainingSession(TrainingSession userTrainingSession){
        trainingSessions.add(userTrainingSession);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserName().equals(user.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", address=" + address +
                ", roleList=" + roleList +
                ", trainingSessions=" + trainingSessions +
                '}';
    }
}
