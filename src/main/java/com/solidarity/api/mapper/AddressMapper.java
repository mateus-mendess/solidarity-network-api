package com.solidarity.api.mapper;

import com.solidarity.api.dto.response.CoordinatesResponse;
import com.solidarity.api.model.entity.Address;
import com.solidarity.api.dto.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressRequest addressRequest);

    @Mapping(target = "latitude", source = "latitude", qualifiedByName = "ToDouble")
    @Mapping(target = "longitude", source = "longitude", qualifiedByName = "ToDouble")
    Address getCoordinatesToAddress(CoordinatesResponse coordinatesResponse);

    @Named("ToDouble")
    default Double ToDouble(String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
