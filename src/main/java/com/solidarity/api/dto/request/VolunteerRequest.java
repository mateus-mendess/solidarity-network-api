package com.solidarity.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class VolunteerRequest extends UserRequest{
        @NotBlank
        //@CPF(message = "Invalid CPF")
        private String cpf;

        @NotBlank
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
        message = "Invalid name, check characters")
        private String name;

        @NotBlank
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
        message = "Invalid name, check characters")
        private String lastName;

        @NotNull
        @Past(message = "Birthday must be in the past")
        private LocalDate birthday;

        @NotBlank
        @Pattern(regexp = "^(?i)(male|female|other)$",
        message = "Gender provided is invalid")
        private String gender;

        private MultipartFile profilePhoto;

        @NotBlank
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$",
        message = "Work must contain only letters")
        private String work;


        private Boolean authorized;
 }
