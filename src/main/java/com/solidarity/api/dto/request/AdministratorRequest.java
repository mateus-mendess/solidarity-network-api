package com.solidarity.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
public class AdministratorRequest {
    @NotBlank
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
            message = "Invalid name, check characters")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
            message = "Invalid name, check characters")
    private String lastName;

    @NotBlank
    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @NotBlank
    @Pattern(regexp = "^(?i)(male|female|other)$",
            message = "Gender provided is invalid")
    private String gender;
}
