package dtos;

import entities.Category;
import entities.TrainingSession;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDTO {
    private Integer id;
    private String categoryName;
    private List<TrainingSessionDTO> trainingSessions = new ArrayList<>();

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        if(category.getTrainingSessions().size() <1) {
            category.getTrainingSessions().forEach( myTrainingSession -> {
                trainingSessions.add(new TrainingSessionDTO(myTrainingSession));
            });
        }
    }

    public Category getEntity() {
        Category category = new Category();
        if (this.id != null) {
            category.setId(this.id);
        }
        category.setCategoryName(this.categoryName);
        List<TrainingSession> myTrainingSessionList = new ArrayList<>();
        this.trainingSessions.forEach(TrainingSessionDTO -> myTrainingSessionList.add(TrainingSessionDTO.getEntity()));
        category.setTrainingSessions(myTrainingSessionList);
        return category;
    }

    public static List<CategoryDTO> getCategoryDTOs(List<Category> categories) {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        categories.forEach(category -> categoryDTOs.add(new CategoryDTO(category)));
        return categoryDTOs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<TrainingSessionDTO> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSessionDTO> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;
        CategoryDTO that = (CategoryDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", trainingSessions=" + trainingSessions +
                '}';
    }
}
