package dtos;

import datafacades.UserFacade;
import entities.Category;
import entities.TrainingSession;
import entities.User;
import errorhandling.API_Exception;
import java.util.*;

public class TrainingSessionDTO {
    private int id;
    private String title;
    private String time;
    private String date;
    private String fullAddress;
    private CategoryDTO category;
    private int maxParticipants;

    private List<UserDTO> users = new ArrayList<>();

    public TrainingSessionDTO(TrainingSession trainingSession) {
        if(trainingSession.getId() !=null) {
            this.id = trainingSession.getId();
        }

        if(trainingSession.getUsers().size() >0) {
            trainingSession.getUsers().forEach(user -> {
                users.add(new UserDTO(user));
            });
        }
        this.title = trainingSession.getTitle();
        this.time = trainingSession.getTime();
        this.date = trainingSession.getDate();
        this.fullAddress = trainingSession.getFullAddress();
        this.category = new CategoryDTO(trainingSession.getCategory());
        this.maxParticipants = trainingSession.getMaxParticipants();
    }

    public TrainingSession getEntity() {
        TrainingSession trainingSession = new TrainingSession();
        if (this.id > 0) {
            trainingSession.setId(this.id);
        }
        if(this.users != null){
            List<User> myUserList = new ArrayList<>();
            this.users.forEach(userDTO -> myUserList.add(userDTO.getEntity()));
            trainingSession.setUsers(myUserList);
        }
        trainingSession.setTitle(this.title);
        trainingSession.setTime(this.time);
        trainingSession.setDate(this.date);
        trainingSession.setFullAddress(this.fullAddress);
        trainingSession.setCategory(this.category.getEntity());
        trainingSession.setMaxParticipants(this.maxParticipants);
        return trainingSession;
    }

    public static List<TrainingSessionDTO> getTrainingSessionDTOs(List<TrainingSession> trainingSessions) {
        List<TrainingSessionDTO> trainingSessionDTOS = new ArrayList<>();
        trainingSessions.forEach(trainingSession -> trainingSessionDTOS.add(new TrainingSessionDTO(trainingSession)));
        return trainingSessionDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSessionDTO that = (TrainingSessionDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TrainingSessionDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", date=" + date +
                ", fullAddress='" + fullAddress + '\'' +
                ", category=" + category +
                ", maxParticipants=" + maxParticipants +
                ", users=" + users +
                '}';
    }
}
