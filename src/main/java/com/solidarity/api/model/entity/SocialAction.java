package com.solidarity.api.model.entity;

import com.solidarity.api.enums.StatusSocialAction;
import com.solidarity.api.enums.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "social_action")
@Entity
public class SocialAction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer volunteersLimit;
    private Integer volunteerRegistered;
    private String image;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    private StatusSocialAction statusSocialAction;

    @ManyToMany
    @JoinTable(name = "social_action_category",
    joinColumns = @JoinColumn(name = "social_action_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Column(name = "postal_code")
    private String postalCode;
    private String neighborhood;
    private String street;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getVolunteersLimit() {
        return volunteersLimit;
    }

    public void setVolunteersLimit(Integer volunteersLimit) {
        this.volunteersLimit = volunteersLimit;
    }

    public Integer getVolunteerRegistered() {
        return volunteerRegistered;
    }

    public void setVolunteerRegistered(Integer volunteerRegistered) {
        this.volunteerRegistered = volunteerRegistered;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public StatusSocialAction getStatusSocialAction() {
        return statusSocialAction;
    }

    public void setStatusSocialAction(StatusSocialAction statusSocialAction) {
        this.statusSocialAction = statusSocialAction;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
