package com.solidarity.api.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cpf;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthday;

    private String gender;

    @ManyToMany(mappedBy = "administrators")
    private List<Organization> organizations = new ArrayList<>();
}
