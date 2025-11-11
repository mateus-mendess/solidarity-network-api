package com.solidarity.api.mapper;

import com.solidarity.api.dto.request.SocialActionRequest;
import com.solidarity.api.dto.response.SocialActionCardResponse;
import com.solidarity.api.model.entity.SocialAction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface SocialActionMapper{

    @Mapping(target = "postalCode", source = "address.postalCode")
    @Mapping(target = "neighborhood", source = "address.neighborhood")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "state", source = "address.state")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "image", ignore = true)
    SocialAction toSocialAction(SocialActionRequest socialActionRequest);

    SocialActionCardResponse toSocialActionResponse(SocialAction socialAction);
}
