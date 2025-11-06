package com.solidarity.api.dto.request;

import com.solidarity.api.enums.Visibility;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class SocialActionRequest {

    @NotBlank(message = "The title cannot be empty.")
    private String title;

    @NotBlank(message = "The description cannot be empty.")
    private String description;

    @NotNull(message = "The start date cannot be empty.")
    @FutureOrPresent(message = "The start date cannot be in the past.")
    private Date startDate;

    @NotNull(message = "The end date cannot be empty.")
    @FutureOrPresent(message = "The end date cannot be in the past.")
    private Date endDate;

    @NotNull(message = "The volunteers limit cannot be empty.")
    private Integer volunteersLimit;

    private MultipartFile image;

    @NotBlank(message = "The visibility cannot be empty.")
    private Visibility visibility;

    @NotBlank(message = "The status cannot be empty.")
    private SocialActionRequest status;

    public SocialActionRequest getStatus() {
        return status;
    }

    public void setStatus(SocialActionRequest status) {
        this.status = status;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Integer getVolunteersLimit() {
        return volunteersLimit;
    }

    public void setVolunteersLimit(Integer volunteersLimit) {
        this.volunteersLimit = volunteersLimit;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
