package com.mert.service;

import com.mert.dto.request.*;

import com.mert.exception.ErrorType;
import com.mert.exception.UserManagerException;
import com.mert.manager.AuthManager;
import com.mert.manager.ServiceManager;
import com.mert.mapper.UserMapper;
import com.mert.repository.UserProfileRepository;
import com.mert.repository.entity.UserProfile;
import com.mert.utility.JwtTokenManager;
import com.mert.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final UserProfileRepository userProfileRepository;
    private final JwtTokenManager jwtTokenManager;

    private final AuthManager authManager;

    public UserProfileService(UserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager, AuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
    }

    public Boolean update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()) throw new UserManagerException(ErrorType.USER_NOT_FOUND);

        if (!dto.getUsername().equals(userProfile.get().getUsername())|| !dto.getEmail().equals(userProfile.get().getUsername())){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            UpdateEmailAndUsernameRequestDto updateEmailAndUsernameRequestDto=UpdateEmailAndUsernameRequestDto.builder()
                    .email(userProfile.get().getEmail())
                    .username(userProfile.get().getUsername())
                    .id(userProfile.get().getAuthId())
                    .build();
            authManager.update(updateEmailAndUsernameRequestDto);
        }
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatarUrl(dto.getAvatarUrl());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());

        return true;
    }
    public Boolean createUser(UserCreateRequestDto dto) {
        try {
            save(UserMapper.INSTANCE.fromCreateRequestToUser(dto));
            return true;
        } catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }
    public Boolean activateStatus(ActivateStatusRequestDto dto) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthId(dto.getAuthId());
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else {
            userProfile.get().setStatus(EStatus.ACTIVE);
            update(userProfile.get());
            return true;
        }
    }
    public Boolean delete(DeleteRequestDto dto){
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(dto.getAuthId());
        if (!dto.getAuthId().equals(userProfile.get().getAuthId()))
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
            userProfile.get().setStatus(EStatus.DELETED);
            return true;
    }

}
