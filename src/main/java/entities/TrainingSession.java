package entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQuery(name = "TrainingSession.deleteAllRows", query = "DELETE from TrainingSession ")
@Table(name = "trainingsession")
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainingsession_id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "time", nullable = false, length = 45)
    private String time;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @ManyToMany
    @JoinTable(name = "user_trainingsessions",
            joinColumns = @JoinColumn(name = "trainingsession_id"),
            inverseJoinColumns = @JoinColumn(name = "user_name"))
    private List<User> users = new ArrayList<>();

    public TrainingSession() {
    }

    public TrainingSession(Integer id, String title, String time, String date, String fullAddress, Category category, Integer maxParticipants) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.date = date;
        this.fullAddress = fullAddress;
        this.category = category;
        this.maxParticipants = maxParticipants;
    }

    public TrainingSession(String title, String time, String date, String fullAddress, Category category, Integer maxParticipants) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.fullAddress = fullAddress;
        this.category = category;
        this.maxParticipants = maxParticipants;
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
        if (!(o instanceof TrainingSession)) return false;
        TrainingSession that = (TrainingSession) o;
        return getId().equals(that.getId());
    }

    public TrainingSession(Integer id, String title, String time, String date, String fullAddress, Category category, Integer maxParticipants, List<User> users) {
        this.id = id;
        this.title = title;
        this.time = time;

        this.date = date;
        this.fullAddress = fullAddress;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.users = users;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
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