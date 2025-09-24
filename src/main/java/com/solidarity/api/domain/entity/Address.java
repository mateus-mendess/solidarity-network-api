package com.solidarity.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "postal_code")
    private String postalCode;

    private String neighborhood;
    private String street;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "ong_id")
    private Organization organization;
}
