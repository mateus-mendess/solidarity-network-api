package com.solidarity.api.mapper;

import com.solidarity.api.domain.entity.Administrator;
import com.solidarity.api.dto.request.AdministratorRequest;
import com.solidarity.api.dto.response.AdministratorResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministratorMapper {
    Administrator toAdministrator(AdministratorRequest administratorRequest);

    AdministratorResponse toAdministratorResponse(Administrator administrator);
}
