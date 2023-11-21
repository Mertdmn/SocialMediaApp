package com.mert.mapper;

import com.mert.dto.request.RegisterRequestDto;
import com.mert.dto.response.RegisterResponseDto;
import com.mert.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth fromRegisterRequestToAuth(RegisterRequestDto dto);

    RegisterResponseDto fromAuthToRegisterResponse(Auth auth);

//    @Mapping(source ="id" ,target = "authId")
//    UserCreateRequestDto fromAuthToUserCreateRequestDto(Auth auth);
}
