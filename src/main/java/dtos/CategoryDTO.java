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
    private List<TrainingSession> trainingSessions;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.trainingSessions = category.getTrainingSessions();
    }

    public Category getEntity() {
        Category category = new Category();
        if (this.id != null) {
            category.setId(this.id);
        }
        category.setCategoryName(this.categoryName);
        category.setTrainingSessions(this.trainingSessions);
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

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
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
