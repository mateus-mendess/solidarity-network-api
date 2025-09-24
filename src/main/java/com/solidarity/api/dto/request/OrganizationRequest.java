package com.solidarity.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class OrganizationRequest extends UserRequest{

    @NotBlank
    @CNPJ(message = "invalid CPF")
    private String cnpj;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
            message = "Invalid name, check characters")
    private String corporateName;

    @NotBlank
    private String about;

    @NotBlank
    @Pattern(regexp = "^\\d{10,12}$",
            message = "Invalid phone number, please check the number")
    private String phone;

    private MultipartFile profilePhoto;

    private MultipartFile coverPhoto;

    @URL(message = "URL invalid")
    private String websiteUrl;

    private List<AddressRequest> addressRequests;
}
