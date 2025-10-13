package com.solidarity.api.mapper;

import com.solidarity.api.model.entity.Volunteer;
import com.solidarity.api.dto.request.VolunteerRequest;
import com.solidarity.api.dto.response.VolunteerResponse;
import com.solidarity.api.enums.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VolunteerMapper {
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "gender", source = "gender")
    Volunteer toVolunteer(VolunteerRequest volunteerRequest);

    @Mapping(target = "email", source = "user.email")
    VolunteerResponse toVolunteerResponse(Volunteer volunteer);

    @Mapping(target = "email", source = "user.email")
    List<VolunteerResponse> toVolunteerResponseList(List<Volunteer> volunteers);

    default Gender map(String genderLabel) {
        return genderLabel != null ? Gender.fromLabel(genderLabel) : null;
    }
}
