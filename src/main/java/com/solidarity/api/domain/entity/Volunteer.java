package com.solidarity.api.domain.entity;

import com.solidarity.api.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "volunteer")
@Entity
public class Volunteer {
    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String cpf;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "profile_photo")
    private String profilePhoto;

    private String work;

    private Boolean authorized;
}
