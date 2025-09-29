package com.solidarity.api.mapper;

import com.solidarity.api.domain.entity.Organization;
import com.solidarity.api.dto.request.OrganizationRequest;
import com.solidarity.api.dto.response.OrganizationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface OrganizationMapper {
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "postalCode", source = "address.postalCode")
    @Mapping(target = "neighborhood", source = "address.neighborhood")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "state", source = "address.state")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "administrators", ignore = true)
    Organization toOrganization(OrganizationRequest organizationRequest);

    @Mapping(target = "email", source = "user.email")
    OrganizationResponse toOrganizationResponse(Organization organization);
}
