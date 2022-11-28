package dtos;

import entities.Category;
import entities.TrainingSession;
import entities.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TrainingSessionDTO {
    private Integer id;
    private String title;
    private String time;
    private Date date;
    private String fullAddress;
    private Category category;
    private Integer maxParticipants;

    private List<User> users;

    public TrainingSessionDTO(TrainingSession trainingSession) {
        this.id = trainingSession.getId();
        this.title = trainingSession.getTitle();
        this.time = trainingSession.getTime();
        this.date = trainingSession.getDate();
        this.fullAddress = trainingSession.getFullAddress();
        this.category = trainingSession.getCategory();
        this.maxParticipants = trainingSession.getMaxParticipants();
        this.users = trainingSession.getUsers();
    }

    public TrainingSession getEntity() {
        TrainingSession trainingSession = new TrainingSession();
        if (this.id != null) {
            trainingSession.setId(this.id);
        }
        trainingSession.setTitle(this.title);
        trainingSession.setTime(this.time);
        trainingSession.setDate(this.date);
        trainingSession.setFullAddress(this.fullAddress);
        trainingSession.setCategory(this.category);
        trainingSession.setMaxParticipants(this.maxParticipants);
        return trainingSession;
    }

    public static List<TrainingSessionDTO> getTrainingSessionDTOs(List<TrainingSession> trainingSessions) {
        List<TrainingSessionDTO> trainingSessionDTOS = new ArrayList<>();
        trainingSessions.forEach(trainingSession -> trainingSessionDTOS.add(new TrainingSessionDTO(trainingSession)));
        return trainingSessionDTOS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingSessionDTO)) return false;
        TrainingSessionDTO that = (TrainingSessionDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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
