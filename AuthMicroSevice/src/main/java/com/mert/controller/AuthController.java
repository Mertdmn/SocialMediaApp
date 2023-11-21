package com.mert.controller;

import com.mert.dto.request.*;
import com.mert.dto.response.RegisterResponseDto;
import com.mert.service.AuthService;
import com.mert.utility.JwtTokenManager;
import com.mert.utility.enums.ERole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AUTH")
public class AuthController {
    private final JwtTokenManager jwtTokenManager;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @GetMapping("/create-token")
    public ResponseEntity<String> createToken(Long id, ERole role){
        return ResponseEntity.ok(jwtTokenManager.createToken(id,role).get());
    }
    @GetMapping("/create-token2")
    public ResponseEntity<String> createToken2(Long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
    }
    @GetMapping("/get-id-from-token")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }
    @GetMapping("/get-role-from-token")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
    }
    @PostMapping("/activate-status")
    public ResponseEntity<Boolean> activateStatus(ActivationRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PutMapping("/update-email-or-username")
    public ResponseEntity<Boolean> update(@RequestBody UpdateEmailAndUsernameRequestDto updateEmailAndUsernameRequestDto){
        return ResponseEntity.ok(authService.updateEmailAndUsername(updateEmailAndUsernameRequestDto));
    }
    @PutMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody DeleteRequestDto dto){
        return ResponseEntity.ok(authService.delete(dto));
    }
}
