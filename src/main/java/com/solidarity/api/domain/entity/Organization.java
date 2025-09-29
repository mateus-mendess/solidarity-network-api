package com.solidarity.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
@Entity
public class Organization {
    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String cnpj;
    private String corporateName;
    private String phone;
    private String about;
    private String profilePhoto;
    private String coverPhoto;
    private String websiteUrl;

    @Column(name = "postal_code")
    private String postalCode;
    private String neighborhood;
    private String street;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "organization_administrator",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "administrator_id")
    )
    private List<Administrator> administrators = new ArrayList<>();
}