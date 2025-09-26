package com.solidarity.api.mapper;

import com.solidarity.api.domain.entity.Address;
import com.solidarity.api.dto.request.AddressRequest;
import com.solidarity.api.dto.response.GeocodingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressRequest addressRequest);

    @Mapping(target = "latitude", source = "lat", qualifiedByName = "ToDouble")
    @Mapping(target = "longitude", source = "lon", qualifiedByName = "ToDouble")
    Address getCoordinatesToAddress(GeocodingResponse geocodingResponse);

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
