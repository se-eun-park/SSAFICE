package com.jetty.ssafficebe.user.converter;

import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    User toUser(SaveUserRequest saveUserRequest);
}
