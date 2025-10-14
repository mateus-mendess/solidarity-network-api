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

    VolunteerResponse toVolunteerResponse(Volunteer volunteer);

    List<VolunteerResponse> toVolunteerResponseList(List<Volunteer> volunteers);

    default Gender map(String genderLabel) {
        return genderLabel != null ? Gender.fromLabel(genderLabel) : null;
    }
}
