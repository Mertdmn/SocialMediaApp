package com.mert.service;

import com.mert.dto.request.*;
import com.mert.dto.response.RegisterResponseDto;
import com.mert.exception.AuthManagerException;
import com.mert.exception.ErrorType;
import com.mert.manager.ServiceManager;
import com.mert.mapper.AuthMapper;
import com.mert.repository.AuthRepository;
import com.mert.repository.entity.Auth;
import com.mert.utility.CodeGenerator;
import com.mert.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthService extends ServiceManager<Auth,Long> {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.fromRegisterRequestToAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        return AuthMapper.INSTANCE.fromAuthToRegisterResponse(auth);
    }

    public Boolean login(LoginRequestDto dto){
        Optional<Auth> authOptional = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());
        if(authOptional.isEmpty()) throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        return true;
    }
    public Boolean activateStatus(ActivationRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if(auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if(dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            return true;
        } else {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_ERROR);
        }
    }

    public Boolean updateEmailAndUsername(UpdateEmailAndUsernameRequestDto updateEmailAndUsernameRequestDto) {
        Optional<Auth> auth=authRepository.findById(updateEmailAndUsernameRequestDto.getId());
        if (auth.isEmpty()) throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        auth.get().setUsername(updateEmailAndUsernameRequestDto.getUsername());
        auth.get().setEmail(updateEmailAndUsernameRequestDto.getEmail());
        update(auth.get());
        return true;
    }
    public Boolean delete(DeleteRequestDto dto){
        Optional<Auth> auth=authRepository.findById(dto.getAuthId());
        if (auth.isEmpty()) throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        auth.get().setStatus(EStatus.DELETED);
        return true;
    }
}
