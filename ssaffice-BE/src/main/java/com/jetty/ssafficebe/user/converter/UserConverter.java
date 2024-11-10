package com.jetty.ssafficebe.user.converter;

import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    User toUser(SaveUserRequest saveUserRequest);

    UserSummary toUserSummary(User user);

    void updateUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

    CreatedBySummary toCreatedBySummary(User user);
}
